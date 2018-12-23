package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.Record;
import app.xlui.target.entity.User;
import app.xlui.target.entity.common.ApiResponse;
import app.xlui.target.service.CheckinService;
import app.xlui.target.service.TargetService;
import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Check in controller.
 */
@RestController
public class CheckInController {
	@Autowired
	private CheckinService checkinService;
	@Autowired
	private TargetService targetService;

	// request checkin
	@RequestMapping(value = "/target/{tid}/checkin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse checkIn(@CurrentUser User user, @PathVariable long tid, @RequestBody @Valid Record param) {
		if (checkinService.checkedToday(tid)) {
			return ApiResponse.of(HttpStatus.NO_CONTENT, "You have checked in today!");
		} else {
			checkinService.checkin(user.getUid(), tid, param.getCheckinDateTime());
			return ApiResponse.of(HttpStatus.OK, "Successfully check in!");
		}
	}

	// re-checkin
	@RequestMapping(value = "/target/{tid}/recheckin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse reCheckIn(@CurrentUser User user, @PathVariable long tid, @RequestBody @Valid Record paramRecord) {
		String reason = AssertUtils.orElse(paramRecord.getReason(), "");
		checkinService.recheckin(user.getUid(), tid, paramRecord.getCheckinDateTime(), reason);
		return new ApiResponse(HttpStatus.OK, "Successfully recheckin!");
	}

	// view this month's checkin record.
	@RequestMapping(value = "/target/{tid}/checkin/{year}/{month}", method = RequestMethod.GET)
	public ApiResponse getCheckinSomeMonth(@PathVariable long tid, @PathVariable int year, @PathVariable int month) {
		var yearMonth = YearMonth.of(year, month);
		var records = checkinService.recordOfMonth(tid, yearMonth.atDay(1));
		Map<Integer, Boolean> map = new HashMap<>();

		// update map mark records as true
		records.stream()
				.map(Record::getCheckinDateTime)
				.map(datetime -> datetime.get(ChronoField.DAY_OF_MONTH))
				.forEach(day -> map.put(day, Boolean.TRUE));
		// update map mark unchecked as false
		Stream.iterate(1, i -> i + 1)
				.limit(yearMonth.lengthOfMonth())
				.filter(i -> !map.containsKey(i))
				.forEach(i -> map.put(i, Boolean.FALSE));
		return ApiResponse.of(HttpStatus.OK, map);
	}

	// view someday's checkin details
	@RequestMapping(value = "/target/{tid}/checkin/{year}/{month}/{day}", method = RequestMethod.GET)
	public ApiResponse getCheckin(@PathVariable long tid, @PathVariable int year, @PathVariable int month, @PathVariable int day) {
		var date = LocalDate.of(year, month, day);
		Record record = checkinService.recordOfSomeday(tid, date);
		// return 200 to avoid too many error in frontend
		if (record == null)
			return new ApiResponse(HttpStatus.NOT_FOUND, "You have not checked in at " + date);
		return ApiResponse.of(HttpStatus.OK, record);
	}

	@RequestMapping(value = "/target/{tid}/statistics", method = RequestMethod.GET)
	public ApiResponse statistics(@PathVariable long tid) {
		Map<String, Object> map = new HashMap<>();
		map.put("checked", checkinService.checkedDays(tid));
		map.put("leftToDone", targetService.leftToDone(tid));
		var target = targetService.findByTid(tid);
		map.put("continuous", target.getContinuous());
		map.put("longestContinuous", target.getMaxContinuous());
		return new ApiResponse(HttpStatus.OK, map);
	}
}

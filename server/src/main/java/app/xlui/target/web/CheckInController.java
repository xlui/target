package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.Record;
import app.xlui.target.entity.User;
import app.xlui.target.entity.common.ApiResponse;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.exception.specify.NotFoundException;
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
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// todo: show specify month's record map.
	// view this month's checkin record.
	@RequestMapping(value = "/target/{tid}/checkin/{year}/{month}", method = RequestMethod.GET)
	public ApiResponse getAllCheckinThisMonth(@PathVariable long tid, @PathVariable int year, @PathVariable int month) {
		var yearMonth = YearMonth.of(year, month);
		var records = checkinService.recordOfMonth(tid, yearMonth.atDay(1));
		AssertUtils.requireNotEmpty(records, () -> new NotFoundException("No checkin records in this month"));
		return new ApiResponse(HttpStatus.OK, records);
	}

	// todo: year-month-day refactor
	// view someday's checkin details
	@RequestMapping(value = "/target/{tid}/checkin/{time}", method = RequestMethod.GET)
	public ApiResponse getCheckin(@CurrentUser User user, @PathVariable long tid, @PathVariable String time) {
		Record record = checkinService.recordOfSomeday(tid, time);
		if (record == null)
			return new ApiResponse(HttpStatus.NOT_FOUND, "You have not checked in at " + time);
		return new ApiResponse(HttpStatus.OK, record);
	}

	// todo: API 精简
	// `time` format: 2018-11-01, IOS_LOCAL_DATE
	@RequestMapping(value = "/target/{tid}/record/{time}", method = RequestMethod.GET)
	public ApiResponse getRecordOfMonth(@PathVariable long tid, @PathVariable String time) {
		List<Record> records;
		try {
			records = checkinService.recordOfMonth(tid, LocalDate.parse(time));
		} catch (DateTimeParseException e) {
			throw new InvalidInputException(e.getMessage());
		}
		return new ApiResponse(HttpStatus.OK, records);
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

package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.Record;
import app.xlui.target.entity.User;
import app.xlui.target.entity.common.ApiResponse;
import app.xlui.target.exception.specify.ForbiddenException;
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
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CheckInController {
	@Autowired
	private CheckinService checkinService;
	@Autowired
	private TargetService targetService;

	// visit check-in record in this month
	@RequestMapping(value = "/target/{tid}/checkin", method = RequestMethod.GET)
	public ApiResponse getAllCheckinThisMonth(@PathVariable long tid) {
		List<Record> records = checkinService.recordOfMonth(tid, LocalDate.now());
		AssertUtils.requireNotEmpty(records, () -> new NotFoundException("No checkin records in this month"));
		return new ApiResponse(HttpStatus.OK, records);
	}

	// check-in today
	@RequestMapping(value = "/target/{tid}/checkin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse checkIn(@CurrentUser User user, @PathVariable long tid, @RequestBody @Valid Record param) {
		AssertUtils.requireEquals(user.getUid(), param.getUid(), () -> new ForbiddenException("Try to checkin for another user! Forbidden"));
		if (checkinService.checkinedToday(tid)) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "You have checkedDays in today!");
		} else {
			checkinService.checkin(param.getUid(), tid, param.getCheckinDateTime());
			return new ApiResponse(HttpStatus.OK, "Successfully check in!");
		}
	}

	// view someday's check-in details
	@RequestMapping(value = "/target/{tid}/checkin/{time}", method = RequestMethod.GET)
	public ApiResponse getCheckin(@CurrentUser User user, @PathVariable long tid, @PathVariable String time) {
		Record record = checkinService.recordOfSomeday(tid, time);
		if (record == null)
			return new ApiResponse(HttpStatus.NOT_FOUND, "You have not checkedDays in at " + time);
		return new ApiResponse(HttpStatus.OK, record);
	}

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
		map.put("continuous", 0);
		map.put("longestContinuous", 0);
		return new ApiResponse(HttpStatus.OK, map);
	}
}

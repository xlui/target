package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.Record;
import app.xlui.target.entity.User;
import app.xlui.target.exception.specify.ForbiddenException;
import app.xlui.target.exception.specify.NotFoundException;
import app.xlui.target.service.CheckinService;
import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CheckInController {
	@Autowired
	private CheckinService checkinService;

	// visit check-in record in this month
	@RequestMapping(value = "/target/{tid}/checkin", method = RequestMethod.GET)
	public ApiResponse getAllCheckinThisMonth(@PathVariable long tid) {
		List<Record> records = checkinService.recordOfThisMonth(tid);
		AssertUtils.requireNotEmpty(records, () -> new NotFoundException("No checkin records in this month"));
		return new ApiResponse(HttpStatus.OK, records);
	}

	// check-in today
	@RequestMapping(value = "/target/{tid}/checkin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse checkIn(@CurrentUser User user, @PathVariable long tid, @RequestBody @Valid Record param) {
		AssertUtils.requireEquals(user.getUid(), param.getUid(), () -> new ForbiddenException("Try to checkin for another user! Forbidden"));
		if (checkinService.checkinedToday(tid)) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "You have checkined today!");
		} else {
			Record record = new Record()
					.setUid(param.getUid())
					.setTid(tid)
					.setCheckinDateTime(param.getCheckinDateTime());
			checkinService.checkin(record);
			return new ApiResponse(HttpStatus.OK, "Successfully checkined!");
		}
	}

	// view someday's check-in details
	@RequestMapping(value = "/target/{tid}/checkin/{time}", method = RequestMethod.GET)
	public ApiResponse getCheckin(@CurrentUser User user, @PathVariable long tid, @PathVariable String time) {
		Record record = AssertUtils.requireNotNull(checkinService.recordOfSomeday(tid, time), () -> new NotFoundException("No checkin record at the specify day!"));
		return new ApiResponse(HttpStatus.OK, record);
	}
}

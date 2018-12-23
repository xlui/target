package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.Record;
import app.xlui.target.entity.User;
import app.xlui.target.entity.common.ApiResponse;
import app.xlui.target.service.CheckinService;
import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * Check in controller.
 */
@RestController
public class CheckInController {
	@Autowired
	private CheckinService checkinService;

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
}

package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.Record;
import app.xlui.target.entity.User;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.service.CheckinService;
import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class CheckInController {
	@Autowired
	private CheckinService checkinService;

	@RequestMapping(value = "/checkin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse checkIn(@CurrentUser User user, @RequestBody @NotNull Record param) {
		AssertUtils.requireEquals(user.getUid(), param.getUid(), () -> new InvalidInputException("Try to checkin for another user! Forbidden"));
		long tid = AssertUtils.requireNotNull(param.getTid(), () -> new InvalidInputException("Missing required argument: tid"));
		AssertUtils.requireNotNull(param.getCheckinDateTime(), () -> new InvalidInputException("Missing required argument: checkin date time"));
		if (checkinService.checkined(tid)) {
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
}

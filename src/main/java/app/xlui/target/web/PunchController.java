package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.Record;
import app.xlui.target.entity.User;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.service.PunchService;
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
public class PunchController {
	@Autowired
	private PunchService punchService;

	@RequestMapping(value = "/punch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse punch(@CurrentUser User user, @RequestBody @NotNull Record param) {
		AssertUtils.assertEquals(user.getUid(), param.getUid(), () -> new InvalidInputException("Try to punch for another user! Forbidden"));
		long tid = AssertUtils.requireNotNull(param.getTid(), () -> new InvalidInputException("Missing required argument: tid"));
		AssertUtils.requireNotNull(param.getPunchDateTime(), () -> new InvalidInputException("Missing required argument: punch date time"));
		if (punchService.punched(tid)) {
			return new ApiResponse(HttpStatus.NO_CONTENT, "You have punched today!");
		} else {
			Record record = new Record()
					.setUid(param.getUid())
					.setTid(tid)
					.setPunchDateTime(param.getPunchDateTime());
			punchService.punch(record);
			return new ApiResponse(HttpStatus.OK, "Successfully punched!");
		}
	}
}

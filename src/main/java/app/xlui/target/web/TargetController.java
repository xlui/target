package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.exception.common.ServerError;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.service.TargetService;
import app.xlui.target.service.UserService;
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
public class TargetController {
	@Autowired
	private UserService userService;
	@Autowired
	private TargetService targetService;

	@RequestMapping(value = "/target", method = RequestMethod.GET)
	public ApiResponse getTargets(@CurrentUser User user) {
		System.out.println("Current user: " + user);
		return new ApiResponse(HttpStatus.OK, targetService.findForUser(user));
	}

	@RequestMapping(value = "/target", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse addTarget(@CurrentUser User user, @RequestBody @NotNull Target param) {
		AssertUtils.assertEquals(user.getUid(), param.getUid(), () -> new InvalidInputException("Trying to submit a target using invalid parameter: uid"));
		AssertUtils.requireValid(param.getTitle(), () -> new InvalidInputException("Target title is invalid!"));
		Target target = new Target()
				.setTitle(param.getTitle())
				.setDescription(param.getDescription())
				.setStartDate(param.getStartDate())
				.setEndDate(param.getEndDate())
				.setPunchStart(param.getPunchStart())
				.setPunchEnd(param.getPunchEnd())
				.setRepeat(param.getRepeat());
		AssertUtils.assertNotZero(targetService.save(target), () -> new ServerError("Failed to save target! Unknown exception occurs, please view server log."));
		return new ApiResponse(HttpStatus.OK, "Successfully add a new target!");
	}
}

package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
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

//	@RequestMapping(value = "/target", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public ApiResponse addTarget(@CurrentUser User user, @RequestBody @NotNull Target target) {
//		AssertUtils.assertEquals(user.getUid(), target.getUid(), () -> new InvalidInputException("Trying to submit a target using invalid parameter: uid"));
//
//	}
}

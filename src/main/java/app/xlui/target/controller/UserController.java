package app.xlui.target.controller;

import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.User;
import app.xlui.target.exception.common.ServerError;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.service.UserService;
import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ApiResponse register(@RequestBody @NotNull User param) {
		AssertUtils.requireValid(param.getUsername(), () -> new InvalidInputException("Username must be not empty!"));
		AssertUtils.requireValid(param.getPassword(), () -> new InvalidInputException("Password must be not empty!"));
		User user = new User(param.getUsername(), param.getPassword());
		if (userService.register(user)) {
			return new ApiResponse(HttpStatus.OK, "Successfully register! Welcome!");
		} else {
			throw new ServerError("Oops! An error occurs while registering...");
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ApiResponse login(@RequestBody @NotNull User param) {
		AssertUtils.requireValid(param.getUsername(), () -> new InvalidInputException("Username must be not empty!"));
		AssertUtils.requireValid(param.getPassword(), () -> new InvalidInputException("Password must be not empty!"));
		if (userService.login(param.getUsername(), param.getPassword())) {
			return new ApiResponse(HttpStatus.OK, "Successfully login!");
		} else {
			throw new InvalidInputException("Username or password is wrong!");
		}
	}
}

package app.xlui.target.controller;

import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.User;
import app.xlui.target.exception.common.ServerError;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.exception.specify.TokenParseException;
import app.xlui.target.service.UserService;
import app.xlui.target.util.AssertUtils;
import app.xlui.target.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
		String username = param.getUsername(), password = param.getPassword();
		AssertUtils.requireValid(username, () -> new InvalidInputException("Username must be not empty!"));
		AssertUtils.requireValid(password, () -> new InvalidInputException("Password must be not empty!"));
		if (userService.login(username, password)) {
			return new ApiResponse(HttpStatus.OK, JwtUtils.generate(username, password));
		} else {
			throw new InvalidInputException("Username or password is wrong!");
		}
	}

	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public ApiResponse token(@RequestHeader("Authorization") @NotNull String token) {
		String username = JwtUtils.username(token);
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new TokenParseException("Invalid token!");
		}
		JwtUtils.verify(token, username, user.getPassword());
		return new ApiResponse(HttpStatus.OK, "Successfully logged in through token.");
	}
}

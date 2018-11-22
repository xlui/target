package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.config.Constant;
import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.Mail;
import app.xlui.target.entity.User;
import app.xlui.target.exception.common.ServerError;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.service.RabbitService;
import app.xlui.target.service.RedisService;
import app.xlui.target.service.UserService;
import app.xlui.target.util.AssertUtils;
import app.xlui.target.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private RabbitService rabbitService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ApiResponse register(@RequestBody @NotNull User param) {
		String username = AssertUtils.requireValid(param.getUsername(), () -> new InvalidInputException("Username must be not empty!"));
		String password = AssertUtils.requireValid(param.getPassword(), () -> new InvalidInputException("Password must be not empty!"));
		User user = new User(username, password);
		if (userService.register(user)) {
			return new ApiResponse(HttpStatus.CREATED, "Successfully register! Welcome!");
		} else {
			throw new ServerError("Oops! An error occurs while registering...");
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ApiResponse login(@RequestBody @NotNull User param) {
		String username = AssertUtils.requireValid(param.getUsername(), () -> new InvalidInputException("Username must be not empty!"));
		String password = AssertUtils.requireValid(param.getPassword(), () -> new InvalidInputException("Password must be not empty!"));
		if (userService.login(username, password)) {
			return new ApiResponse(HttpStatus.OK, JwtUtils.generate(username, password));
		} else {
			throw new InvalidInputException("Username or password is wrong!");
		}
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse change(@RequestBody @NotNull User paramUser, @CurrentUser User user) {
		String paramUsername = AssertUtils.requireValid(paramUser.getUsername(), () -> new InvalidInputException("Username is invalid!"));
		String paramPassword = AssertUtils.requireValid(paramUser.getPassword(), () -> new InvalidInputException("Password is invalid!"));
		AssertUtils.assertEquals(paramUsername, user.getUsername(), () -> new InvalidInputException("Username mismatch with token! Please don't try to modify another user's password"));
		userService.updatePassword(paramUsername, paramPassword);
		return new ApiResponse(HttpStatus.OK, "Successfully update user's password");
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse reset(@RequestBody @NotNull User paramUser, @RequestParam(value = "token", required = false) String paramToken) {
		String username = AssertUtils.requireValid(paramUser.getUsername(), () -> new InvalidInputException("Username is invalid!"));
		AssertUtils.requireNotNull(userService.findByUsername(username), () -> new InvalidInputException("Username is invalid! This user have not register!"));
		if (paramToken == null) {
			String token = UUID.randomUUID().toString();
			String current = Constant.currentTime();
			redisService.set(token, username, Constant.forgetTokenTimeout);
			rabbitService.sendEmail(new Mail(username, token, current));
			return new ApiResponse(HttpStatus.OK, "Successfully send password reset Email.");
		} else {
			// verify token
			String originUsername = AssertUtils.requireNotNull(redisService.get(paramToken), () -> new InvalidInputException("Token is invalid or expired!"));
			AssertUtils.assertEquals(originUsername, username, () -> new InvalidInputException("Username mismatch! Please don't modify the password reset url!"));
			String newPassword = AssertUtils.requireNotNull(paramUser.getPassword(), () -> new InvalidInputException("New password must not be null!"));
			// update password
			userService.updatePassword(username, newPassword);
			return new ApiResponse(HttpStatus.CREATED, "Successfully update password!");
		}
	}
}

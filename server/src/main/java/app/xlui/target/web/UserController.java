package app.xlui.target.web;

import app.xlui.target.config.Constant;
import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.User;
import app.xlui.target.exception.common.ServerError;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.service.RabbitService;
import app.xlui.target.service.RedisService;
import app.xlui.target.service.UserService;
import app.xlui.target.util.AssertUtils;
import app.xlui.target.util.JwtUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

	@RequestMapping(value = "/forget", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponse forget(@RequestBody @NotNull User param) {
		String username = AssertUtils.requireValid(param.getUsername(), () -> new InvalidInputException("Username is invalid!"));
		String token  = UUID.randomUUID().toString();
		String current = Constant.currentTime();
		redisService.set(token, username, Constant.forgetTokenTimeout);
		rabbitService.sendEmail(username, token, current);
		return new ApiResponse(HttpStatus.OK, "Successfully send password reset Email.");
	}
}

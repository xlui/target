package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.ApiResponse;
import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.exception.common.ServerError;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.exception.specify.NotFoundException;
import app.xlui.target.service.TargetService;
import app.xlui.target.service.UserService;
import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class TargetController {
	@Autowired
	private UserService userService;
	@Autowired
	private TargetService targetService;

	@RequestMapping(value = "/target", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse getTargets(@CurrentUser User user) {
		return new ApiResponse(HttpStatus.OK, targetService.findForUser(user));
	}

	@RequestMapping(value = "/target", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse addTarget(@CurrentUser User user, @RequestBody @NotNull Target param) {
		AssertUtils.requireEquals(user.getUid(), param.getUid(), () -> new InvalidInputException("Trying to submit a target using invalid parameter: uid"));
		AssertUtils.requireValid(param.getTitle(), () -> new InvalidInputException("Target title is invalid!"));
		Target target = new Target()
				.setTitle(param.getTitle())
				.setDescription(param.getDescription())
				.setStartDate(param.getStartDate())
				.setEndDate(param.getEndDate())
				.setPunchStart(param.getPunchStart())
				.setPunchEnd(param.getPunchEnd())
				.setRepeat(param.getRepeat());
		AssertUtils.requireNotZero(targetService.save(target), () -> new ServerError("Failed to save target! Unknown exception occurs, please view server log."));
		return new ApiResponse(HttpStatus.CREATED, "Successfully add a new target!");
	}

	@RequestMapping(value = "/target/{tid}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse getTarget(@CurrentUser User user, @PathVariable long tid) {
		Target target = targetService.findByTid(tid);
		AssertUtils.requireNotNull(target, () -> new InvalidInputException("Tid is invalid!"));
		return new ApiResponse(HttpStatus.OK, target);
	}

	@RequestMapping(value = "/target/{tid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse updateTarget(@CurrentUser User user, @PathVariable long tid, @RequestBody @NotNull Target param) {
		AssertUtils.requireNotNull(tid, () -> new InvalidInputException("Require tid not null!"));
		AssertUtils.requireEquals(user.getUid(), param.getUid(), () -> new InvalidInputException("Trying to submit a target using invalid parameter: uid"));
		AssertUtils.requireValid(param.getTitle(), () -> new InvalidInputException("Target title is invalid!"));
		Target target = new Target()
				.setTid(tid)
				.setUid(user.getUid())
				.setTitle(param.getTitle())
				.setDescription(param.getDescription())
				.setStartDate(param.getStartDate())
				.setEndDate(param.getEndDate())
				.setPunchStart(param.getPunchStart())
				.setPunchEnd(param.getPunchEnd())
				.setRepeat(param.getRepeat());
		AssertUtils.requireNotZero(targetService.update(target), () -> new NotFoundException("The target id which you want to update is invalid!"));
		return new ApiResponse(HttpStatus.NO_CONTENT, "Successfully update target");
	}

	@RequestMapping(value = "/target/{tid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse deleteTarget(@CurrentUser User user, @PathVariable long tid) {
		AssertUtils.requireNotNull(tid, () -> new InvalidInputException("Require tid not null!"));
		AssertUtils.requireNotZero(targetService.delete(tid), () -> new NotFoundException("The target id is invalid! There isn't a target with the request tid at server!"));
		return new ApiResponse(HttpStatus.OK, "Successfully delete target: " + tid);
	}
}

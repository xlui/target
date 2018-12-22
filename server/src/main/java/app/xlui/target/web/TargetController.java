package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.common.ApiResponse;
import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.entity.enums.Week;
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

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Target controller.
 */
@RestController
public class TargetController {
	@Autowired
	private UserService userService;
	@Autowired
	private TargetService targetService;

	@RequestMapping(value = "/target", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse getTargets(@CurrentUser User user, @RequestParam(required = false, defaultValue = "true") boolean filter) {
		var targets = targetService.findForUser(user);
		if (filter) {
			var week = Week.toByte(LocalDateTime.now());
			targets = targets.stream()
					.filter(target -> targetService.isValidRepeat(target.getTid(), week))
					.collect(Collectors.toList());
		}
		return new ApiResponse(HttpStatus.OK, targets);
	}

	@RequestMapping(value = "/target", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse addTarget(@CurrentUser User user, @RequestBody @Valid Target param) {
		Target target = new Target()
				.setUid(user.getUid())
				.setTitle(param.getTitle())
				.setDescription(param.getDescription())
				.setStartDate(param.getStartDate())
				.setEndDate(param.getEndDate())
				.setCheckinStart(param.getCheckinStart())
				.setCheckinEnd(param.getCheckinEnd())
				.setRepeat(param.getRepeat());
		AssertUtils.requireNotZero(targetService.save(target), () -> new ServerError("Failed to save target! Unknown exception occurs, please view server log."));
		return new ApiResponse(HttpStatus.CREATED, "Successfully add a new target!");
	}

	@RequestMapping(value = "/target/{tid}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse getTarget(@CurrentUser User user, @PathVariable long tid) {
		Target target = targetService.findByTid(tid);
		AssertUtils.requireNotNull(target, () -> new NotFoundException("Tid is invalid!"));
		return new ApiResponse(HttpStatus.OK, target);
	}

	@RequestMapping(value = "/target/{tid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ApiResponse updateTarget(@CurrentUser User user, @PathVariable long tid, @RequestBody @Valid Target param) {
		Target target = new Target()
				.setTid(tid)
				.setUid(user.getUid())
				.setTitle(param.getTitle())
				.setDescription(param.getDescription())
				.setStartDate(param.getStartDate())
				.setEndDate(param.getEndDate())
				.setCheckinStart(param.getCheckinStart())
				.setCheckinEnd(param.getCheckinEnd())
				.setRepeat(param.getRepeat());
		AssertUtils.requireNotZero(targetService.update(target), () -> new NotFoundException("The target id which you want to update is invalid!"));
		return new ApiResponse(HttpStatus.NO_CONTENT, "Successfully update target");
	}

	@RequestMapping(value = "/target/{tid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse deleteTarget(@CurrentUser User user, @PathVariable long tid) {
		AssertUtils.requireNotNull(tid, () -> new InvalidInputException("Require tid not null!"));
		if (targetService.exist(tid)) {
			targetService.delete(tid);
			return new ApiResponse(HttpStatus.OK, "Successfully delete target: " + tid);
		} else {
			throw new NotFoundException("No target bind with the request tid!");
		}
	}
}

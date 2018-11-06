package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.service.TargetService;
import app.xlui.target.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TargetController {
	@Autowired
	private UserService userService;
	@Autowired
	private TargetService targetService;

	@RequestMapping(value = "/target", method = RequestMethod.GET)
	public List<Target> targets(@CurrentUser User user) {
		System.out.println("Current user: " + user);
		return targetService.findForUser(user);
	}
}

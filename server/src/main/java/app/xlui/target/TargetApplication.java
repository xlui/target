package app.xlui.target;

import app.xlui.target.entity.User;
import app.xlui.target.service.TargetService;
import app.xlui.target.service.UserService;
import app.xlui.target.util.FakeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@SpringBootApplication
public class TargetApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;
	@Autowired
	private TargetService targetService;

	public static void main(String[] args) {
		SpringApplication.run(TargetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Current time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));
		System.out.println("Server start!");
		init();
	}

	@RequestMapping("/token")
	public String test() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return "pass token auth: " + user.getUsername();
	}

	@RequestMapping("/t/init")
	public String init() {
		FakeUtils.fakeTarget(10);
		FakeUtils.fakeRecord(20);
		return "init succeed!";
	}
}

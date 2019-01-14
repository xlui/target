package app.xlui.target;

import app.xlui.target.service.TargetService;
import app.xlui.target.service.UserService;
import app.xlui.target.util.FakeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
	@Value("${target.init}")
	private boolean init;
	@Value("${spring.profiles.active}")
	private String profiles;

	public static void main(String[] args) {
		SpringApplication.run(TargetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Server start!");
		System.out.println("Current active profiles: " + profiles);
		System.out.println("Current time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")) + ".");
		if (init) {
			System.out.println("Initializing database....");
			this.initNormal();
			System.out.println("Initialize succeed!");
		}
	}

	@RequestMapping("/t/init/normal")
	public String initNormal() {
		System.out.println("Initializing user....");
		FakeUtils.fakeUser(10);
		System.out.println("Initializing target....");
		FakeUtils.fakeTarget(30);
		System.out.println("Initializing record....");
		FakeUtils.fakeRecord(100);
		return "init succeed!";
	}

	@RequestMapping("/t/init/heavy")
	public String initHeavy() {
		System.out.println("Initializing user....");
		FakeUtils.fakeUser(10);
		System.out.println("Initializing target....");
		FakeUtils.fakeTarget(586);
		System.out.println("Initializing record....");
		FakeUtils.fakeRecord(2000);
		return "init succeed!";
	}
}

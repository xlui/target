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

	public static void main(String[] args) {
		SpringApplication.run(TargetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Current time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));
		System.out.println("Server start!");
		if (init) {
			System.out.println("Initializing database....");
			this.init();
			System.out.println("Initialize succeed!");
		}
	}

	@RequestMapping("/t/init")
	public String init() {
		System.out.println("Initializing target....");
		FakeUtils.fakeTarget(10);
		System.out.println("Initializing record....");
		FakeUtils.fakeRecord(20);
		return "init succeed!";
	}
}

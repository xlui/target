package app.xlui.target;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TargetApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TargetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Server start!");
	}
}

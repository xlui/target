package app.xlui.target.config;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Constant {
	int expire = 30;
	String rabbitQueue = "mail.queue";
	String rabbitTopicExchange = "mail.exchange";
	String rabbitRoutingKey = "mail.to.#";

	static long expire() {
		return System.currentTimeMillis() + Duration.ofMinutes(expire).toMillis();
	}

	static String currentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}

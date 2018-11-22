package app.xlui.target.config;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Constant {
	Duration loginTokenExpire = Duration.ofMinutes(60);
	Duration forgetTokenTimeout = Duration.ofMinutes(10);
	String rabbitQueue = "mail.queue";
	String rabbitExchange = "mail.exchange";
	String rabbitRoutingKey = "mail.to";

	static long expire() {
		return System.currentTimeMillis() + loginTokenExpire.toMillis();
	}

	static String currentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}

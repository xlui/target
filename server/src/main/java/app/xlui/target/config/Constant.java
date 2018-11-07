package app.xlui.target.config;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Constant {
	private static final int expire = 30;

	public static long expire() {
		return System.currentTimeMillis() + Duration.ofMinutes(expire).toMillis();
	}

	public static String currentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}

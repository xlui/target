package app.xlui.target.config;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Global constants. In order to avoid hard-coded.
 */
public interface Constant {
	String host = "http://localhost:8080";
	Duration loginTokenExpire = Duration.ofHours(24);
	Duration forgetTokenTimeout = Duration.ofMinutes(10);
	String rabbitQueue = "mail.queue";
	String rabbitExchange = "mail.exchange";
	String rabbitRoutingKey = "mail.to";
	String mailSubject = "Password Reset";
	String mailContentTemplate = "你好，user。这是你的密码重置链接： " +
			Constant.host + "/reset?token=tokenplaceholder 。" +
			"此链接 " + Constant.forgetTokenTimeout.toMinutes() + " 分钟内有效。";
	String zsetRankWeekly = "checkin.rank.weekly";
	String zsetRankMonthly = "checkin.rank.monthly";
	String zsetRankTotal = "checkin.rank.total";

	/**
	 * Return the timestamp when token expires.
	 */
	static long expire() {
		return System.currentTimeMillis() + loginTokenExpire.toMillis();
	}

	/**
	 * Return current time in specify format.
	 */
	static String currentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}

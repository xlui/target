package app.xlui.target.util;

import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.entity.enums.Gender;
import app.xlui.target.service.CheckinService;
import app.xlui.target.service.TargetService;
import app.xlui.target.service.UserService;
import com.github.javafaker.Faker;
import me.tongfei.progressbar.ProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Generate fake data.
 */
@Component
public class FakeUtils {
	private static Faker faker = new Faker();
	private static UserService userService;
	private static TargetService targetService;
	private static CheckinService checkinService;

	@Autowired
	public FakeUtils(UserService userService, TargetService targetService, CheckinService checkinService) {
		FakeUtils.userService = userService;
		FakeUtils.targetService = targetService;
		FakeUtils.checkinService = checkinService;
	}

	public static Faker faker() {
		return faker;
	}

	public static void fakeUser(int count) {
		ProgressBar.wrap(Stream.iterate(0, i -> i + 1).limit(count), "Fake User").forEach(i -> {
			User user = new User()
					.setNickname(faker.funnyName().name())
					.setGender(faker.bool().bool() ? Gender.MAN : Gender.WOMAN)
					.setBirthday(LocalDate.parse(faker.date().birthday().toString()))
					.setUsername(faker.internet().emailAddress())
					.setPassword(faker.internet().password())
					.setRegistered(
							faker.date()
									.between(faker.date().past(20, TimeUnit.DAYS),
											faker.date().future(20, TimeUnit.DAYS))
									.toInstant()
									.atZone(ZoneId.systemDefault())
									.toLocalDateTime());
			userService.save(user);
		});
	}

	public static void fakeTarget(int count) {
		targetService.clear();
		List<Long> uids = userService.findUIDs();
		ProgressBar.wrap(Stream.iterate(0, i -> i + 1).limit(count), "Fake Target").forEach(i -> {
			Target target = new Target()
					.setUid(uids.get(faker.random().nextInt(uids.size())))
					.setTitle(faker.book().title())
					.setDescription(faker.lorem().sentence())
					.setStartDate(faker.date().past(20, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
					.setEndDate(faker.date().future(20, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
					.setCheckinStart(LocalTime.of(faker.random().nextInt(0, 11), faker.random().nextInt(60)))
					.setCheckinEnd(LocalTime.of(faker.random().nextInt(12, 23), faker.random().nextInt(60)));
			target.setCreated(
					faker.date()
							.past(10, TimeUnit.DAYS, java.sql.Date.valueOf(target.getStartDate()))
							.toInstant()
							.atZone(ZoneId.systemDefault())
							.toLocalDateTime()
			);
			targetService.save(target);
		});
	}

	public static void fakeRecord(int count) {
		checkinService.clearAll();
		List<Long> tids = targetService.findTids();
		ProgressBar.wrap(Stream.iterate(0, i -> i + 1).limit(count), "Fake Record").forEach(i -> {
			long tid = tids.get(faker.random().nextInt(tids.size()));
			Target target = targetService.findByTid(tid);
			LocalDateTime fakeDateTime = null;
			do {
				java.util.Date fakeDate = faker.date().between(Date.valueOf(target.getStartDate()), Date.valueOf(target.getEndDate()));
				java.util.Date fakeTime = faker.date().between(Time.valueOf(target.getCheckinStart()), Time.valueOf(target.getCheckinEnd()));
				LocalDate date = LocalDate.ofInstant(fakeDate.toInstant(), ZoneId.systemDefault());
				LocalTime time = LocalTime.ofInstant(fakeTime.toInstant(), ZoneId.systemDefault());
				fakeDateTime = LocalDateTime.of(date, time);
			} while (checkinService.checkedSomeday(tid, fakeDateTime.toLocalDate()) ||
					!targetService.isValidTime(tid, fakeDateTime.toLocalTime()));
			checkinService.checkin(target.getUid(), tid, fakeDateTime);
		});
	}
}

package app.xlui.target.util;

import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.entity.enums.Gender;
import app.xlui.target.service.CheckinService;
import app.xlui.target.service.TargetService;
import app.xlui.target.service.UserService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
		for (int i = 0; i < count; i++) {
			User user = new User()
					.setNickname(faker.funnyName().name())
					.setGender(faker.bool().bool() ? Gender.MAN : Gender.WOMAN)
					.setBirthday(LocalDate.parse(faker.date().birthday().toString()))
					.setUsername(faker.internet().emailAddress())
					.setPassword(faker.internet().password());
			userService.save(user);
		}
	}

	public static void fakeTarget(int count) {
		targetService.clear();
		List<Long> uids = userService.findUIDs();
		for (int i = 0; i < count; i++) {
			Target target = new Target()
					.setUid(uids.get(faker.random().nextInt(uids.size())))
					.setTitle(faker.book().title())
					.setDescription(faker.lorem().sentence())
					.setStartDate(faker.date().past(20, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
					.setEndDate(faker.date().future(20, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
					.setCheckinStart(LocalTime.of(faker.random().nextInt(0, 11), faker.random().nextInt(60)))
					.setCheckinEnd(LocalTime.of(faker.random().nextInt(12, 23), faker.random().nextInt(60)))
					.setRepeat(faker.random().nextInt(0, 127).byteValue());
			targetService.save(target);
		}
	}

	public static void fakeRecord(int count) {
		checkinService.clearAll();
		List<Long> tids = targetService.findTids();
		for (int i = 0; i < count; i++) {
			long tid = tids.get(faker.random().nextInt(tids.size()));
			Target target = targetService.findByTid(tid);
			LocalDateTime fakeDateTime = null;
			do {
				fakeDateTime = faker.date().future(10, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				System.out.println("tid: " + tid);
				System.out.println("Target check in start time: " + target.getCheckinStart());
				System.out.println("Target check in end time: " + target.getCheckinEnd());
				System.out.println("Fake datetime: " + fakeDateTime);
				System.out.println("Target checked in? " + checkinService.checkedSomeday(tid, fakeDateTime.toLocalDate()));
				System.out.println("Fake time is valid time? " + targetService.isValidTime(tid, fakeDateTime.toLocalTime()));
			} while (checkinService.checkedSomeday(tid, fakeDateTime.toLocalDate()) || !targetService.isValidTime(tid, fakeDateTime.toLocalTime()));
			checkinService.checkin(target.getUid(), tid, fakeDateTime);
		}
	}
}

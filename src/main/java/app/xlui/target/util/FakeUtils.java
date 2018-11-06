package app.xlui.target.util;

import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.entity.enums.Gender;
import app.xlui.target.service.TargetService;
import app.xlui.target.service.UserService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class FakeUtils {
	private static Faker faker = new Faker();
	private static UserService userService;
	private static TargetService targetService;

	@Autowired
	public FakeUtils(UserService userService, TargetService targetService) {
		FakeUtils.userService = userService;
		FakeUtils.targetService = targetService;
	}

	public static void fakeUser(int count) {
		for (int i = 0; i < count; i++) {
			User user = new User();
			user.setNickname(faker.funnyName().name());
			user.setGender(faker.bool().bool() ? Gender.MAN : Gender.WOMAN);
			user.setBirthday(LocalDate.parse(faker.date().birthday().toString()));
			user.setUsername(faker.internet().emailAddress());
			user.setPassword(faker.internet().password());
			userService.save(user);
		}
	}

	public static void fakeTarget(int count) {
		targetService.clear();
		List<Long> uids = userService.findUIDs();
		for (int i = 0; i < count; i++) {
			Target target = new Target();
			target.setUid(uids.get(faker.random().nextInt(uids.size())));
			target.setTitle(faker.book().title());
			target.setDescription(faker.lorem().sentence());
			target.setStartDate(faker.date().past(20, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			target.setEndDate(faker.date().future(20, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			target.setPunchStart(LocalTime.of(faker.random().nextInt(0, 12), faker.random().nextInt(60)));
			target.setPunchEnd(LocalTime.of(faker.random().nextInt(12, 23), faker.random().nextInt(60)));
			target.setRepeat(faker.random().nextInt(0, 127).byteValue());
			targetService.save(target);
		}
	}
}

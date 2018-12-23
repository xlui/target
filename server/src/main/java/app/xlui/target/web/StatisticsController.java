package app.xlui.target.web;

import app.xlui.target.annotation.CurrentUser;
import app.xlui.target.entity.Record;
import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.entity.common.ApiResponse;
import app.xlui.target.service.CheckinService;
import app.xlui.target.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Stream;

@RestController
public class StatisticsController {
	private final CheckinService checkinService;
	private final TargetService targetService;

	@Autowired
	public StatisticsController(CheckinService checkinService, TargetService targetService) {
		this.checkinService = checkinService;
		this.targetService = targetService;
	}

	/**
	 * Someone target's total statistics
	 */
	@RequestMapping(value = "/target/{tid}/statistics", method = RequestMethod.GET)
	public ApiResponse statistics(@PathVariable long tid) {
		Map<String, Object> map = new HashMap<>();
		map.put("checked", checkinService.checkedDays(tid));
		map.put("leftToDone", targetService.leftToDone(tid));
		var target = targetService.findByTid(tid);
		map.put("continuous", target.getContinuous());
		map.put("longestContinuous", target.getMaxContinuous());
		return new ApiResponse(HttpStatus.OK, map);
	}

	/**
	 * Someone target's monthly statistics
	 */
	@RequestMapping(value = "/target/{tid}/statistics/{year}/{month}", method = RequestMethod.GET)
	public ApiResponse getCheckinSomeMonth(@PathVariable long tid, @PathVariable int year, @PathVariable int month) {
		var yearMonth = YearMonth.of(year, month);
		var records = checkinService.recordOfMonth(tid, yearMonth.atDay(1));
		Map<Integer, Boolean> map = new HashMap<>();

		// update map mark records as true
		records.stream()
				.map(Record::getCheckinDateTime)
				.map(datetime -> datetime.get(ChronoField.DAY_OF_MONTH))
				.forEach(day -> map.put(day, Boolean.TRUE));
		// update map mark unchecked as false
		Stream.iterate(1, i -> i + 1)
				.limit(yearMonth.lengthOfMonth())
				.filter(i -> !map.containsKey(i))
				.forEach(i -> map.put(i, Boolean.FALSE));
		return ApiResponse.of(HttpStatus.OK, map);
	}

	@RequestMapping(value = "/weekly/{year}/{month}/{day}", method = RequestMethod.GET)
	public ApiResponse weekly(@CurrentUser User user, @PathVariable int year, @PathVariable int month, @PathVariable int day) {
		double sum = 0, complete = 0;
		var date = LocalDate.of(year, month, day);
		var monday = date.with(DayOfWeek.MONDAY);
		var sunday = date.with(DayOfWeek.SUNDAY);
		var targets = targetService.findByUser(user);

		complete = checkinService.countRecordsBetween(user, monday, sunday);

		for (Target target : targets) {
			if (target.getStartDate().compareTo(monday) < 0) {
				if (target.getEndDate().compareTo(sunday) > 0) {
					sum += Period.between(monday, sunday).getDays() + 1;
				} else {
					sum += Period.between(monday, target.getEndDate()).getDays() + 1;
				}
			} else {
				if (target.getEndDate().compareTo(sunday) > 0) {
					sum += Period.between(target.getStartDate(), sunday).getDays() + 1;
				} else {
					sum += Period.between(target.getStartDate(), target.getEndDate()).getDays() + 1;
				}
			}
		}

		return ApiResponse.of(HttpStatus.OK, Map.of(
				"WeekStart", monday,
				"WeekEnd", sunday,
				"TotalCheckIn", complete,
				"ShouldCheckIn", sum,
				"CompletePercentage", complete / sum * 100
		));
	}

	/**
	 * todo: this API cost a lot.
	 * <p>
	 * Show user's journey in this checkin project.
	 */
	@RequestMapping(value = "/journey", method = RequestMethod.GET)
	public ApiResponse journey(@CurrentUser User user) {
		PriorityQueue<Map.Entry<LocalDateTime, String>> queue = new PriorityQueue<>(Map.Entry.comparingByKey());
		// user
		queue.add(Map.entry(user.getRegistered(), "Register"));
		// targets
		var targets = targetService.findByUser(user);
		var map = new HashMap<>();
		for (Target target : targets) {
			queue.add(Map.entry(target.getCreated(), "Create new target: " + target.getTitle()));
			map.put(target.getTid(), target.getTitle());
		}
		// records
		var records = checkinService.recordOfUser(user.getUid());
		for (Record record : records) {
			var datetime = record.getCheckinDateTime();
			queue.add(Map.entry(datetime, "Checkin: " + map.get(record.getTid())));
		}
		// output
		List<Map> result = new ArrayList<>(queue.size());
		while (!queue.isEmpty()) {
			var entity = queue.poll();
			result.add(Map.of(
					"date", entity.getKey().toLocalDate(),
					"journey", entity.getValue(),
					"time", entity.getKey().toLocalTime())
			);
		}
		return ApiResponse.of(HttpStatus.OK, result);
	}
}

package app.xlui.target.service;

import app.xlui.target.entity.Record;
import app.xlui.target.entity.User;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.mapper.RecordMapper;
import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Checkin service. This service provides api for checkin and related operations.
 */
@Service
public class CheckinService {
	@Autowired
	private RecordMapper recordMapper;
	@Autowired
	private TargetService targetService;

	public List<Record> findAll() {
		return recordMapper.findAll();
	}

	public List<Record> recordsBetween(LocalDate start, LocalDate end) {
		return recordMapper.findAllBetween(start, end);
	}

	public List<Record> recordsBetween(User user, LocalDate start, LocalDate end) {
		return recordMapper.findBetween(user.getUid(), start, end);
	}

	public int countRecordsBetween(User user, LocalDate start, LocalDate end) {
		return recordMapper.countBetween(user.getUid(), start, end);
	}

	public List<Record> recordOfMonth(long tid, LocalDate date) {
		return recordMapper.findRecordSomeMonth(tid, date);
	}

	public Record recordOfSomeday(long tid, String time) {
		return recordMapper.findRecordSomeday(tid, LocalDate.parse(time, DateTimeFormatter.ISO_DATE));
	}

	public Record recordOfSomeday(long tid, LocalDate localDate) {
		return recordMapper.findRecordSomeday(tid, localDate);
	}

	public List<Record> recordOfUser(long uid) {
		return recordMapper.findByUid(uid);
	}

	public void checkin(long uid, long tid, LocalDateTime datetime) {
		// validate checkin date
		AssertUtils.requireTrue(targetService.isValidDate(tid, datetime.toLocalDate()), () -> new InvalidInputException("Please don't try check in at a invalid date!"));
		// make sure have not checked at the request date
		AssertUtils.requireFalse(checkedSomeday(tid, datetime.toLocalDate()), () -> new InvalidInputException("You have checked in today!"));
		// make sure not too early
		AssertUtils.requireFalse(targetService.early(tid, datetime.toLocalTime()), () -> new InvalidInputException("It is too early to check in now!"));
		// make sure not too late
		AssertUtils.requireFalse(targetService.late(tid, datetime.toLocalTime()), () -> new InvalidInputException("Oops! You have missed the last time to check in today!"));
		recordMapper.save(uid, tid, datetime);

		// update continuous checkin data
		var target = targetService.findByTid(tid);
		LocalDate yesterday = datetime.toLocalDate().minusDays(1L);
		if (yesterday.compareTo(target.getStartDate()) >= 0) {
			// this condition indicate that yesterday is within the valid period
			var yesterdayRecord = recordOfSomeday(tid, yesterday);
			if (yesterdayRecord != null && !yesterdayRecord.isReCheckIn()) {
				// exclude rechecin record
				target.setContinuous(target.getContinuous() + 1);
			} else {
				// this checkin is the first checkin after last miss
				target.setContinuous(1);
			}
		} else {
			// this checkin is the first valid checkin of target
			target.setContinuous(1);
		}
		// update max continuous
		if (target.getMaxContinuous() < target.getContinuous()) {
			target.setMaxContinuous(target.getContinuous());
		}
		targetService.update(target);
	}

	public void recheckin(long uid, long tid, LocalDateTime datetime, String reason) {
		// make sure have not checked at the request date
		AssertUtils.requireFalse(checkedSomeday(tid, datetime.toLocalDate()), () -> new InvalidInputException("You have checked in at " + datetime.toLocalDate() + ", please don't try recheckin!"));
		recordMapper.recheckin(uid, tid, datetime, reason);
	}

	public boolean checkedSomeday(long tid, String time) {
		return recordOfSomeday(tid, time) != null;
	}

	public boolean checkedSomeday(long tid, LocalDate localDate) {
		return recordOfSomeday(tid, localDate) != null;
	}

	public boolean checkedToday(long tid) {
		return recordMapper.countRecordToday(tid) > 0;
	}

	public int checkedDays(long tid) {
		return recordMapper.checkedDays(tid);
	}

	public void clearAll() {
		recordMapper.clearAll();
	}
}

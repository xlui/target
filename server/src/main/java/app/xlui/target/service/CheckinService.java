package app.xlui.target.service;

import app.xlui.target.entity.Record;
import app.xlui.target.entity.enums.Week;
import app.xlui.target.exception.specify.ForbiddenException;
import app.xlui.target.exception.specify.InvalidInputException;
import app.xlui.target.mapper.RecordMapper;
import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CheckinService {
	@Autowired
	private RecordMapper recordMapper;
	@Autowired
	private TargetService targetService;

	public List<Record> recordOfMonth(long tid, LocalDate date) {
		return recordMapper.findRecordSomeMonth(tid, date);
	}

	public Record recordOfSomeday(long tid, String time) {
		return recordMapper.findRecordSomeday(tid, LocalDate.parse(time, DateTimeFormatter.ISO_DATE));
	}

	public Record recordOfSomeday(long tid, LocalDate localDate) {
		return recordMapper.findRecordSomeday(tid, localDate);
	}

	public boolean checkedSomeday(long tid, String time) {
		return recordOfSomeday(tid, time) != null;
	}

	public boolean checkedSomeday(long tid, LocalDate localDate) {
		return recordOfSomeday(tid, localDate) != null;
	}

	public void checkin(long uid, long tid, LocalDateTime datetime) {
		// validate repeat
		AssertUtils.requireTrue(targetService.isValidRepeat(tid, Week.toByte(Week.valueOf(datetime.getDayOfWeek().toString()))), () -> new ForbiddenException("Forbidden! You should not try checking in today!(as repeat defined)"));
		// make sure have not checked at the request date
		AssertUtils.requireFalse(checkedSomeday(tid, datetime.toLocalDate()), () -> new InvalidInputException("You have checked in today!"));
		// make sure not too early
		AssertUtils.requireFalse(targetService.early(tid, datetime.toLocalTime()), () -> new InvalidInputException("It is too early to check in now!"));
		// make sure not too late
		AssertUtils.requireFalse(targetService.late(tid, datetime.toLocalTime()), () -> new InvalidInputException("Oops! You have missed the last time to check in today!"));
		recordMapper.save(uid, tid, datetime);

		// todo: run following target in thread pool.
		// update continuous checkin data
		var target = targetService.findByTid(tid);
		LocalDate lastValidDate = datetime.toLocalDate().minusDays(1L);
		while (targetService.isNotValidRepeat(tid, Week.toByte(lastValidDate.getDayOfWeek())) &&
				lastValidDate.compareTo(target.getStartDate()) > 0) {
			lastValidDate = lastValidDate.minusDays(1L);
			System.out.println("checkin for tid: " + tid + " fakedate: " + lastValidDate + " is valid: " + targetService.isValidRepeat(tid, Week.toByte(lastValidDate.getDayOfWeek())));
		}
		// lastValidDate is between startDate and endDate
		// this condition indicate that lastValidDate is a valid repeat
		if (lastValidDate.compareTo(target.getStartDate()) > 0) {
			if (checkedSomeday(tid, lastValidDate)) {
				// todo: add a field in t_record to keep track of continuous check in days related to specify record
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
			// update max continuous
			target.setMaxContinuous(target.getContinuous());
		}
		targetService.update(target);
	}

	public boolean checkinedToday(long tid) {
		return recordMapper.coundRecordToday(tid) > 0;
	}

	public int checkedDays(long tid) {
		return recordMapper.checkedDays(tid);
	}

	public void clearAll() {
		recordMapper.clearAll();
	}
}

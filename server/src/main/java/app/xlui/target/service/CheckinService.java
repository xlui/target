package app.xlui.target.service;

import app.xlui.target.entity.Record;
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

	public List<Record> recordOfThisMonth(long tid) {
		return recordMapper.findRecordThisMonth(tid);
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

	public int checkin(long uid, long tid, LocalDateTime datetime) {
		AssertUtils.requireFalse(checkedSomeday(tid, datetime.toLocalDate()), () -> new InvalidInputException("You have checked in today!"));
		AssertUtils.requireFalse(targetService.early(tid, datetime.toLocalTime()), () -> new InvalidInputException("It is too early to check in now!"));
		AssertUtils.requireFalse(targetService.late(tid, datetime.toLocalTime()), () -> new InvalidInputException("Oops! You have missed the last time to check in today!"));
		return recordMapper.save(uid, tid, datetime);
	}

	public boolean checkinedToday(long tid) {
		return recordMapper.coundRecordToday(tid) > 0;
	}

	public void clearAll() {
		recordMapper.clearAll();
	}
}

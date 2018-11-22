package app.xlui.target.service;

import app.xlui.target.entity.Record;
import app.xlui.target.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CheckinService {
	@Autowired
	private RecordMapper recordMapper;

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

	public int checkin(Record record) {
		// todo: validate checkin day
		// todo: validate checkin time
		return recordMapper.save(record);
	}

	public boolean checkinedToday(long tid) {
		return recordMapper.coundRecordToday(tid) > 0;
	}

	public void clearAll() {
		recordMapper.clearAll();
	}
}

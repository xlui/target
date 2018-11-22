package app.xlui.target.service;

import app.xlui.target.entity.Record;
import app.xlui.target.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckinService {
	@Autowired
	private RecordMapper recordMapper;

	public int checkin(Record record) {
		return recordMapper.save(record);
	}

	public boolean checkined(long tid) {
		return recordMapper.findRecordToday(tid) > 0;
	}

	public void clearAll() {
		recordMapper.clearAll();
	}
}

package app.xlui.target.service;

import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.mapper.RecordMapper;
import app.xlui.target.mapper.TargetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Target server. This service provides api for target operations.
 */
@Service
@Validated
public class TargetService {
	@Autowired
	private TargetMapper targetMapper;
	@Autowired
	private RecordMapper recordMapper;

	public Target findByTid(long tid) {
		return targetMapper.findByTID(tid);
	}

	public List<Target> findByUser(@NotNull User user) {
		return targetMapper.findByUID(user.getUid());
	}

	public List<Target> findValidTargets(User user, LocalDate date) {
		var targets = findByUser(user);
		return targets.stream()
				.filter(target -> isValidDate(target.getTid(), date))
				.collect(Collectors.toList());
	}

	public List<Long> findTids() {
		return targetMapper.findTIDs();
	}

	public int update(Target target) {
		return targetMapper.update(target);
	}

	public int save(Target target) {
		return targetMapper.save(target);
	}

	public void delete(Target target) {
		delete(target.getTid());
	}

	public void delete(long tid) {
		targetMapper.delete(tid);
		recordMapper.delete(tid);
	}

	public void clear() {
		targetMapper.clear();
	}

	public boolean exist(long tid) {
		return findByTid(tid) != null;
	}

	public boolean early(long tid, LocalTime time) {
		return targetMapper.early(tid, time) == 1;
	}

	public boolean late(long tid, LocalTime time) {
		return targetMapper.late(tid, time) == 1;
	}

	public boolean isValidDate(long tid, LocalDate date) {
		return targetMapper.isValidDate(tid, date) == 1;
	}

	public boolean isValidTime(long tid, LocalTime time) {
		return targetMapper.isValidTime(tid, time) == 1;
	}

	public boolean isEnd(long tid) {
		return targetMapper.isEnd(tid) == 1;
	}

	public long leftToDone(long tid) {
		var target = findByTid(tid);
		return LocalDate.now().until(target.getEndDate(), ChronoUnit.DAYS);
	}
}

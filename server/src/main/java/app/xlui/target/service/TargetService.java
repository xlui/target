package app.xlui.target.service;

import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.mapper.TargetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Service
public class TargetService {
	@Autowired
	private TargetMapper targetMapper;

	public Target findByTid(long tid) {
		return targetMapper.findByTID(tid);
	}

	public List<Target> findForUser(@NotNull User user) {
		return targetMapper.findByUID(user.getUid());
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

	public int delete(Target target) {
		return delete(target.getTid());
	}

	public int delete(long tid) {
		return targetMapper.delete(tid);
	}

	public void clear() {
		targetMapper.clear();
	}

	public boolean exist(long tid) {
		return findByTid(tid) != null;
	}

	public boolean isValidTime(long tid, LocalTime time) {
		return targetMapper.isValidTime(tid, time) == 1;
	}
}

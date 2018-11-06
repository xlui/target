package app.xlui.target.service;

import app.xlui.target.entity.Target;
import app.xlui.target.entity.User;
import app.xlui.target.mapper.TargetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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

	public int save(Target target) {
		return targetMapper.save(target);
	}

	public void clear() {
		targetMapper.clear();
	}
}

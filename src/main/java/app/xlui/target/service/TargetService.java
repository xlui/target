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

	public List<Target> findForUser(@NotNull User user) {
		return targetMapper.findByUID(user.getUid());
	}

	public int save(Target target) {
		return targetMapper.save(target);
	}

	public void clear() {
		targetMapper.clear();
	}
}

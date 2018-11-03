package app.xlui.target.service;

import app.xlui.target.entity.User;
import app.xlui.target.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserMapper userMapper;

	@Autowired
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public boolean register(User user) {
		return userMapper.insert(user) > 0;
	}

}

package app.xlui.target.service;

import app.xlui.target.entity.User;
import app.xlui.target.exception.specify.NullInputException;
import app.xlui.target.mapper.UserMapper;
import app.xlui.target.util.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	private final UserMapper userMapper;

	@Autowired
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	public User findByUsernameChecked(String username) {
		User user = findByUsername(username);
		return AssertUtils.requireNotNull(user, () -> new NullInputException("The username have not been registered!"));
	}

	public List<Long> findUIDs() {
		return userMapper.findUIDs();
	}

	public int updatePassword(String username, String newPassword) {
		return userMapper.updatePassword(username, newPassword);
	}

	public int save(User user) {
		return userMapper.save(user);
	}

	public boolean register(User user) {
		return userMapper.save(user) > 0;
	}

	public boolean login(String username, String password) {
		Optional<User> user = Optional.ofNullable(userMapper.findByUsername(username));
		return user.map(User::getPassword)
				.map(password::equals)
				.orElse(false);
	}
}

package app.xlui.target.controller;

import app.xlui.target.entity.User;
import app.xlui.target.entity.enums.Gender;
import app.xlui.target.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
public class WebController {
	@Autowired
	private UserMapper userMapper;

	@RequestMapping("save")
	@ResponseBody
	public String save() {
		User user = new User();
		user.setUsername("i@xlui");
		user.setPassword("xluipassword");
		user.setBirthday(LocalDate.of(1997, 1, 1));
		user.setGender(Gender.MAN);
		user.setNickname("xlui");
		userMapper.insert(user);
		return "success!";
	}

	@RequestMapping("load")
	@ResponseBody
	public User load() {
		return userMapper.findByUID(1);
	}
}

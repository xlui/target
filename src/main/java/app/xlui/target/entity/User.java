package app.xlui.target.entity;

import app.xlui.target.entity.enums.Gender;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private long uid;				// user id
//	private String avatar;			// 头像
	private String nickname;		// 昵称
	private Gender gender;			// 性别
	private LocalDateTime birthday;	// 生日
	private String username;		// 用户名
	private String password;		// 密码
//	private String salt;			// 密码的盐

	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.nickname = username;
		this.gender = Gender.UNKNOWN;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

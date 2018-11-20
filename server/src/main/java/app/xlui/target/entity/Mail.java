package app.xlui.target.entity;

import java.io.Serializable;

public class Mail implements Serializable {
	private static final long serialVersionUID = 1L;

	private String receiver;
	private String token;
	private String date;

	public Mail() {
	}

	public Mail(String receiver, String token, String date) {
		this.receiver = receiver;
		this.token = token;
		this.date = date;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}

package app.xlui.target.web;

import app.xlui.target.entity.Mail;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {
	public void receiveMessage(String message) {
		System.out.println("Received: " + message);
	}

	public void receiveMessage(Mail mail) {
		System.out.println("Prepare to send mail to user");
		System.out.println("Password reset request time: " + mail.getDate());
		// todo: send password reset mail
	}
}

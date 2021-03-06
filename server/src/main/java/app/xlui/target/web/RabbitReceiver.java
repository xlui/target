package app.xlui.target.web;

import app.xlui.target.entity.common.Mail;
import app.xlui.target.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Receive message from rabbitmq and resolve.
 */
@Component
public class RabbitReceiver {
	@Autowired
	private MailService mailService;

	public void receiveMessage(String message) {
		System.out.println("Received: " + message);
	}

	public void receiveMessage(Mail mail) {
		mailService.sendSimpleMail(mail);
	}
}

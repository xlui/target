package app.xlui.target.service;

import app.xlui.target.entity.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String from;
	private final String mailSubject = "Password Reset";
	private final String contentTemplate = "你好，user。你的密码重置链接：http://localhost:8080/reset?token=tokenplaceholder";


	public void sendSimpleMail(Mail mail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(mail.getReceiver());
		message.setSubject(mailSubject);
		message.setText(contentTemplate.replace("user", mail.getReceiver()).replace("tokenplaceholder", mail.getToken()));
		try {
			mailSender.send(message);
			System.out.println("邮件已发送给 " + mail.getReceiver());
		} catch (Exception e) {
			System.out.println("邮件发送失败！");
		}
	}

	public void sendHtmlMail(Mail mail) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(mail.getReceiver());
			helper.setSubject(mailSubject);
			helper.setText(contentTemplate, true);
			mailSender.send(message);
			System.out.println("成功发送 HTML 邮件");
		} catch (MessagingException e) {
			System.out.println("发送 HTML 邮件时发生异常");
			e.printStackTrace();
		}
	}
}

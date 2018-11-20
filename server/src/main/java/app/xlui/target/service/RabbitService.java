package app.xlui.target.service;

import app.xlui.target.config.Constant;
import app.xlui.target.entity.Mail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendEmail(String to, String token, String date) {
		rabbitTemplate.convertAndSend(Constant.rabbitExchange, Constant.rabbitRoutingKey, new Mail(to, token, date));
	}
}

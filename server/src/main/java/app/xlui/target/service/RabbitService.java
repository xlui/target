package app.xlui.target.service;

import app.xlui.target.config.Constant;
import app.xlui.target.entity.common.Mail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Rabbitmq service. This server provides api for send message({@code Object}) to
 * rabbitmq.
 */
@Service
public class RabbitService {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendEmail(Mail mail) {
		rabbitTemplate.convertAndSend(Constant.rabbitExchange, Constant.rabbitRoutingKey, mail);
	}
}

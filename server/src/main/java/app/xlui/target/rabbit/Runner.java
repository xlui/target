//package app.xlui.target.rabbit;
//
//import app.xlui.target.config.Constant;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Runner implements CommandLineRunner {
//	private RabbitTemplate rabbitTemplate;
//	private Receiver receiver;
//
//	public Runner(RabbitTemplate rabbitTemplate, Receiver receiver) {
//		this.rabbitTemplate = rabbitTemplate;
//		this.receiver = receiver;
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("Sending message...");
//		rabbitTemplate.convertAndSend(Constant.rabbitTopicExchange, "mail.to.xlui", "Hello from RabbitMQ!");
//	}
//}

package app.xlui.target.config;

import app.xlui.target.config.Constant;
import app.xlui.target.web.RabbitReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
	@Bean
	Queue queue() {
		return new Queue(Constant.rabbitQueue, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(Constant.rabbitExchange, true, false);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constant.rabbitRoutingKey);
	}

	@Bean
	MessageListenerAdapter listenerAdapter(RabbitReceiver rabbitReceiver) {
		return new MessageListenerAdapter(rabbitReceiver, "receiveMessage");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(Constant.rabbitQueue);
		container.setMessageListener(listenerAdapter);
		return container;
	}
}

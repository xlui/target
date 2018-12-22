package app.xlui.target.config;

import app.xlui.target.web.RabbitReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for RabbitMQ client.
 *
 * For rabbitmq sender, we don't need to define much, we just need to send a message
 * to related <code>exchange</code> with proper <code>routing-key</code>. And that's
 * all.
 *
 * For rabbitmq client, we need to define <code>Queue</code>, <code>Exchange</code>
 * and the <code>binding</code> between Queue and Exchange with a routing-key.
 *
 * If you have defined is yourself, you needn't to do it again in the code. Although
 * due of lacking detect means for undefined scene this is not suggested. You'll just
 * need to define the listener class and specify the listener method. RabbitMQ provide
 * an annotation {@link org.springframework.amqp.rabbit.annotation.RabbitListener} to
 * simplify the process. What we need to do is just place the annotation in front of
 * our listener method.
 *
 * related:
 * 	{@link app.xlui.target.service.RabbitService}
 * 	{@link app.xlui.target.web.RabbitReceiver}
 */
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

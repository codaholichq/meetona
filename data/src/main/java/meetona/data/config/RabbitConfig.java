package meetona.data.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class RabbitConfig implements RabbitListenerConfigurer {

    @Value("${rabbitmq.queue[0].name}")
    private String userQueueName;

    @Value("${rabbitmq.queue[1].name}")
    private String unitQueueName;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing[0].key}")
    private String userRoutingKey;

    @Value("${rabbitmq.routing[1].key}")
    private String unitRoutingKey;


    @Bean
    public Queue userQueue() {
        return new Queue(userQueueName);
    }

    @Bean
    public Queue unitQueue() {
        return new Queue(unitQueueName);
    }

    @Bean
    public TopicExchange TopicExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding binding(final Queue userQueue, final TopicExchange topicExchange) {
        return BindingBuilder.bind(userQueue).to(topicExchange).with(userRoutingKey);
    }

    @Bean
    Binding unitBinding(final Queue unitQueue, final TopicExchange topicExchange) {
        return BindingBuilder.bind(unitQueue).to(topicExchange).with(unitRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        var messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

}

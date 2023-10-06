package meetona.data.config;

import meetona.data.constants.RabbitConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
public class RabbitConfig implements RabbitListenerConfigurer {

    @Bean
    public Queue userQueue() {
        return new Queue(RabbitConstants.USER_QUEUE);
    }

    @Bean
    public Queue unitQueue() {
        return new Queue(RabbitConstants.UNIT_QUEUE);
    }

    @Bean
    public Queue memberQueue() {
        return new Queue(RabbitConstants.MEMBER_QUEUE);
    }

    @Bean
    public Queue meetingQueue() {
        return new Queue(RabbitConstants.MEETING_QUEUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConstants.EXCHANGE);
    }

    @Bean
    public Declarables bindings(
            final Queue userQueue,
            final Queue unitQueue,
            final Queue memberQueue,
            final Queue meetingQueue,
            final TopicExchange topicExchange
    ) {
        return new Declarables(
                BindingBuilder.bind(userQueue).to(topicExchange).with(RabbitConstants.USER_ROUTING_KEY),
                BindingBuilder.bind(unitQueue).to(topicExchange).with(RabbitConstants.UNIT_ROUTING_KEY),
                BindingBuilder.bind(memberQueue).to(topicExchange).with(RabbitConstants.MEMBER_ROUTING_KEY),
                BindingBuilder.bind(meetingQueue).to(topicExchange).with(RabbitConstants.MEETING_ROUTING_KEY)
        );
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

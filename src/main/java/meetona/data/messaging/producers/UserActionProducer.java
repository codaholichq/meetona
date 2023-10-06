package meetona.data.messaging.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.entity.User;
import meetona.core.payload.response.UserDto;
import meetona.data.constants.RabbitConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(UserDto userDto) {
        log.info("Message sent => {}", userDto.toString());
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.USER_ROUTING_KEY, userDto);
    }

    public void sendMessage(User user) {
        log.info("Message sent => {}", user.toString());
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.USER_ROUTING_KEY, user);
    }
}

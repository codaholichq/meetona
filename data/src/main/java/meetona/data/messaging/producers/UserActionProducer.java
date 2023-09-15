package meetona.data.messaging.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.payload.response.UserDto;
import meetona.core.entity.User;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionProducer {

    private final Binding binding;
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(UserDto userDto) {
        log.info("Message sent => {}", userDto.toString());
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), userDto);
    }

    public void sendMessage(User user) {
        log.info("Message sent => {}", user.toString());
        rabbitTemplate.convertAndSend(binding.getExchange(), binding.getRoutingKey(), user);
    }
}

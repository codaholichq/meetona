package meetona.data.messaging.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.payload.response.UnitDto;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitActionProducer {

    private final Binding binding;
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(UnitDto unitDto) {
        log.info("Message sent => {}", unitDto.toString());
        rabbitTemplate.convertAndSend(binding.getExchange(), "${rabbitmq.queue[1].name}", unitDto);
    }

    public void sendMessage(UUID id, UnitDto unitDto) {
        log.info("Message sent => {} {}", id, unitDto.toString());
        rabbitTemplate.convertAndSend(binding.getExchange(), "${rabbitmq.queue[1].name}", unitDto);
    }

    public void sendMessage(UUID id) {
        log.info("Message sent => {}", id);
        rabbitTemplate.convertAndSend(binding.getExchange(), "${rabbitmq.queue[1].name}", id);
    }
}

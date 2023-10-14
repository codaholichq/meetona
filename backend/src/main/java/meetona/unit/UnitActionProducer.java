package meetona.unit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.shared.constants.RabbitConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitActionProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(UnitDto unitDto) {
        log.info("Message sent => {}", unitDto.toString());
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.UNIT_ROUTING_KEY, unitDto);
    }

    public void sendMessage(UUID id, UnitDto unitDto) {
        log.info("Message sent => {} {}", id, unitDto.toString());
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.UNIT_ROUTING_KEY, unitDto);
    }

    public void sendMessage(UUID id) {
        log.info("Message sent => {}", id);
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.UNIT_ROUTING_KEY, id);
    }
}

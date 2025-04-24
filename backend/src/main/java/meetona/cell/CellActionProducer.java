package meetona.cell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.shared.constants.RabbitConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CellActionProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(CellDto cellDto) {
        log.info("Message sent => {}", cellDto.toString());
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.UNIT_ROUTING_KEY, cellDto);
    }

    public void sendMessage(UUID id, CellDto cellDto) {
        log.info("Message sent => {} {}", id, cellDto.toString());
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.UNIT_ROUTING_KEY, cellDto);
    }

    public void sendMessage(UUID id) {
        log.info("Message sent => {}", id);
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.UNIT_ROUTING_KEY, id);
    }
}

package meetona.cell;

import lombok.extern.slf4j.Slf4j;
import meetona.shared.constants.RabbitConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RabbitListener(queues = RabbitConstants.UNIT_QUEUE)
public class CellActionConsumer {

    public void fetchMessage(CellDto cellDto) {
        log.info("Received message => {}", cellDto);
    }

    public void fetchMessage(UUID id, CellDto cellDto) {
        log.info("Received message => {} {}", id, cellDto);
    }

    public void fetchMessage(UUID id) {
        log.info("Received message => {}", id);
    }
}

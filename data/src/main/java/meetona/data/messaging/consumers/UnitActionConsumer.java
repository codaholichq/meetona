package meetona.data.messaging.consumers;

import lombok.extern.slf4j.Slf4j;
import meetona.core.payload.response.UnitDto;
import meetona.data.constants.RabbitConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RabbitListener(queues = RabbitConstants.UNIT_QUEUE)
public class UnitActionConsumer {

    public void fetchMessage(UnitDto unitDto) {
        log.info("Received message => {}", unitDto);
    }

    public void fetchMessage(UUID id, UnitDto unitDto) {
        log.info("Received message => {} {}", id, unitDto);
    }

    public void fetchMessage(UUID id) {
        log.info("Received message => {}", id);
    }
}

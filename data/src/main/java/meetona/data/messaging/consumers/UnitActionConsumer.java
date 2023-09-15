package meetona.data.messaging.consumers;

import lombok.extern.slf4j.Slf4j;
import meetona.core.payload.response.UnitDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UnitActionConsumer {

    @RabbitListener(queues = {"${rabbitmq.queue[1].name}"})
    public void fetchMessage(UnitDto unitDto) {
        log.info("Received message => {}", unitDto);
    }

    @RabbitListener(queues = {"${rabbitmq.queue[1].name}"})
    public void fetchMessage(UUID id, UnitDto unitDto) {
        log.info("Received message => {} {}", id, unitDto);
    }

    @RabbitListener(queues = {"${rabbitmq.queue[1].name}"})
    public void fetchMessage(UUID id) {
        log.info("Received message => {}", id);
    }
}

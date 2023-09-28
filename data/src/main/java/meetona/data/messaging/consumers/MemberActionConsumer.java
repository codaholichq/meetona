package meetona.data.messaging.consumers;

import lombok.extern.slf4j.Slf4j;
import meetona.core.payload.response.MemberDto;
import meetona.data.constants.RabbitConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RabbitListener(queues = RabbitConstants.MEMBER_QUEUE)
public class MemberActionConsumer {

    public void fetchMessage(MemberDto dto) {
        log.info("Received message => {}", dto);
    }

    public void fetchMessage(UUID id, MemberDto dto) {
        log.info("Received message => {} {}", id, dto);
    }

    public void fetchMessage(UUID id) {
        log.info("Received message => {}", id);
    }
}

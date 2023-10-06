package meetona.features.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.shared.constants.RabbitConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberActionProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(MemberDto dto) {
        log.info("Message sent => {}", dto.toString());
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.MEMBER_ROUTING_KEY, dto);
    }

    public void sendMessage(UUID id, MemberDto dto) {
        log.info("Message sent => {} {}", id, dto.toString());
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.MEMBER_ROUTING_KEY, dto);
    }

    public void sendMessage(UUID id) {
        log.info("Message sent => {}", id);
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE, RabbitConstants.MEMBER_ROUTING_KEY, id);
    }
}

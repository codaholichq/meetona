package meetona.data.messaging.consumers;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.payload.response.UserDto;
import meetona.core.entity.User;
import meetona.data.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionConsumer {

    private final MailService mailService;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void fetchMessage(UserDto userDto) throws TemplateException, MessagingException, IOException {

        mailService.sendUserActionEmail(userDto.email(), "Login", "Successful");
        log.info("Received message => {}", userDto);
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void fetchMessage(User user) throws TemplateException, MessagingException, IOException {

        mailService.sendUserActionEmail(user.getEmail(), "Register", "Successful");
        log.info("Received message => {}", user);
    }
}

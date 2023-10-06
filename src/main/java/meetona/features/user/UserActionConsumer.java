package meetona.features.user;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.features.notification.MailService;
import meetona.shared.constants.RabbitConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@RabbitListener(queues = RabbitConstants.USER_QUEUE)
public class UserActionConsumer {

    private final MailService mailService;

    public void fetchMessage(UserDto userDto) throws TemplateException, MessagingException, IOException {

        mailService.sendUserActionEmail(userDto.email(), "Login", "Successful");
        log.info("Received message => {}", userDto);
    }

    public void fetchMessage(User user) throws TemplateException, MessagingException, IOException {

        mailService.sendUserActionEmail(user.getEmail(), "Register", "Successful");
        log.info("Received message => {}", user);
    }
}

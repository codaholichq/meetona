package meetona.data.service;

import meetona.data.Dto.request.MailRequest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final Configuration templateConfig;

    @Value("${app.velocity.templates.location}")
    private String basePackagePath;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${app.token.password.reset.duration}")
    private Long expiration;

    public MailService(JavaMailSender mailSender, Configuration templateConfig) {
        this.mailSender = mailSender;
        this.templateConfig = templateConfig;
    }


    public void sendEmailVerification(String emailVerificationUrl, String to)
            throws IOException, TemplateException, MessagingException {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("Email Verification [RCNLagos]");
        mailRequest.setTo(to);
        mailRequest.setFrom(mailFrom);
        mailRequest.getModel().put("userName", to);
        mailRequest.getModel().put("emailVerificationUrl", emailVerificationUrl);

        templateConfig.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfig.getTemplate("email_verification.html");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailRequest.getModel());
        mailRequest.setContent(mailContent);
        send(mailRequest);
    }

    /**
     * Setting the mail parameters.Send the reset link to the respective user's mail
     */
    public void sendResetLink(String resetPasswordLink, String to)
            throws IOException, TemplateException, MessagingException {
        Long expirationInMinutes = TimeUnit.MILLISECONDS.toMinutes(expiration);
        String expirationInMinutesString = expirationInMinutes.toString();
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("Password Reset Link [Meetona]");
        mailRequest.setTo(to);
        mailRequest.setFrom(mailFrom);
        mailRequest.getModel().put("username", to);
        mailRequest.getModel().put("resetPasswordLink", resetPasswordLink);
        mailRequest.getModel().put("expirationTime", expirationInMinutesString);

        templateConfig.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfig.getTemplate("reset_link.html");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailRequest.getModel());
        mailRequest.setContent(mailContent);
        send(mailRequest);
    }

    /**
     * Send an email to the user indicating an account change event with the correct
     * status
     */
    public void sendAccountActivityEmail(String to, String action, String actionStatus)
            throws IOException, TemplateException, MessagingException {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("Account Activity");
        mailRequest.setTo(to);
        mailRequest.setFrom(mailFrom);
        mailRequest.getModel().put("title", "Account Activity");
        mailRequest.getModel().put("datetime", "10:56AM");
        mailRequest.getModel().put("ipAddress", "192.168.41.120");
        mailRequest.getModel().put("browser", "Brave Browser");
        mailRequest.getModel().put("resetPasswordLink", "http://meetona.com/resetpassword");
        mailRequest.getModel().put("contactUsLink", "https://meetona.com/contactus");
        mailRequest.getModel().put("companyName", "Meetona");
        mailRequest.getModel().put("email", to);
        mailRequest.getModel().put("userName", to);
        mailRequest.getModel().put("action", action);
        mailRequest.getModel().put("actionStatus", actionStatus);

        templateConfig.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfig.getTemplate("account_activity.html");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailRequest.getModel());
        mailRequest.setContent(mailContent);
        send(mailRequest);
    }

    /**
     * Sends a simple mail as a MIME Multipart message
     */
    @Async("taskExecutor")
    public void send(MailRequest mailRequest) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setTo(mailRequest.getTo());
        helper.setText(mailRequest.getContent(), true);
        helper.setSubject(mailRequest.getSubject());
        helper.setFrom(mailRequest.getFrom());
        mailSender.send(message);
    }

}

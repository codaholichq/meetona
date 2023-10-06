package meetona.features.notification;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MailRequest {
    private String from;
    private String to;
    private String subject;
    private String content;
    private Map<String, String> model;

    public MailRequest() {
        model = new HashMap<>();
    }

    public MailRequest(String from, String to, String subject, String content, Map<String, String> model) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.model = model;
    }
}

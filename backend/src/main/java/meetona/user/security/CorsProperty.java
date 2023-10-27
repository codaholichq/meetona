package meetona.user.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app.cors")
public class CorsProperty {
    private String[] origins;
    private String[] headers;
    private String[] methods;
    private long maxAge;
}

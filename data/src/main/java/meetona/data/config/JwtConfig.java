package meetona.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app.jwt")
public class JwtConfig {
    private String secret;
    private String header;
    private long expiration;
    private long refreshExpiration;
}

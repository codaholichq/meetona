package meetona.data.config;

import meetona.core.entity.User;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class WebConfig {

    @Bean(name = "taskExecutor")
    public Executor executor(){
        ThreadPoolTaskExecutor task = new ThreadPoolTaskExecutor();
        task.setCorePoolSize(10);
        task.setMaxPoolSize(20);
        task.setQueueCapacity(200);
        task.setThreadNamePrefix("userThread-");
        task.initialize();
        return task;
    }


    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                Object principal = auth.getPrincipal();
                if (principal instanceof User) {
                    return Optional.of(((User) principal).getUsername());
                }
            }
            return Optional.empty();
        };
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("*"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfig.setMaxAge(Duration.ofHours(1));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}

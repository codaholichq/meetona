package meetona.data.config;

import meetona.core.entity.User;
import meetona.data.repository.UserRepository;
import meetona.data.security.AuthEntryPoint;
import meetona.data.security.AuthFilter;
import meetona.data.security.AuthManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    protected static final String[] WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                return User.builder()
                        .password(passwordEncoder().encode(user.getPassword()))
                        .username(user.getUsername())
                        .roles(user.getRoles())
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthFilter authFilter,
            AuthEntryPoint authEntryPoint,
            AuthManager authManager
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(x -> x.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITELIST).permitAll()
                        .requestMatchers("/api/user/**").permitAll()
                        .anyRequest().authenticated())
                .authenticationManager(authManager)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}

package meetona.data.runners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetona.core.entity.User;
import meetona.core.enums.Role;
import meetona.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminRunner implements CommandLineRunner {

    @Value("${app.admin.firstname}")
    private String FIRSTNAME;

    @Value("${app.admin.lastname}")
    private String LASTNAME;

    @Value("${app.admin.username}")
    private String USERNAME;

    @Value("${app.admin.email}")
    private String EMAIL;

    @Value("${app.admin.password}")
    private String PASSWORD;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    Collection<Role> roles = Set.of(Role.ADMIN);

    @Override
    public void run(String... args) throws Exception {
        boolean existsAdmin = userRepository.existsByRoles(roles);

        if (!existsAdmin) {
            User adminUser = User.builder()
                    .firstname(FIRSTNAME)
                    .lastname(LASTNAME)
                    .username(USERNAME)
                    .email(EMAIL)
                    .roles(Set.of(Role.ADMIN))
                    .isEmailVerified(true)
                    .password(passwordEncoder.encode(PASSWORD))
                    .build();

            userRepository.save(adminUser);
            log.info("User with ADMIN role created successfully");
        }
    }
}

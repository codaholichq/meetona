package meetona.data.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import meetona.core.util.JsonUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Authentication failed";

        if (authException instanceof LockedException) {
            status = HttpStatus.LOCKED;
            message = "Your account has been locked. Please contact support.";
        } else if (authException instanceof DisabledException) {
            status = HttpStatus.FORBIDDEN;
            message = "Your account has been disabled. Please contact support.";
        } else if (authException instanceof CredentialsExpiredException) {
            status = HttpStatus.FORBIDDEN;
            message = "Your credentials have expired. Please reset your password.";
        } else if (authException instanceof AccountExpiredException) {
            status = HttpStatus.FORBIDDEN;
            message = "Your account has expired. Please contact support.";
        }

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", message);

        response.getWriter().write(JsonUtils.toJson(responseBody));
    }
}
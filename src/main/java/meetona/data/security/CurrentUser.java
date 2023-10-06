package meetona.data.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * Custom annotation to access currently authenticated user in the codebase. Works the same as
 * AuthenticationPrincipal. Created to reduce dependency on Spring Security
 */

@Documented
@AuthenticationPrincipal
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE})
public @interface CurrentUser {
}
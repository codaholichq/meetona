package meetona.data.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthManager implements AuthenticationManager {

    private final TokenProvider tokenProvider;

    public AuthManager(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        String username = tokenProvider.getUsernameFromToken(token);
        if (!tokenProvider.validateToken(token)) {
            throw new BadCredentialsException("Invalid token");
        }

        Claims claims = tokenProvider.getClaims(token);
        List<?> rolesMap = claims.get("role", List.class);
        List<GrantedAuthority> authorities = rolesMap.stream()
                .map(role -> new SimpleGrantedAuthority((String) role))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}

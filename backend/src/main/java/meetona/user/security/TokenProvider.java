package meetona.user.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;


@Slf4j
@Component
public class TokenProvider {

    private SecretKeySpec key;
    private final JwtProperty jwtProperty;

    public TokenProvider(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    @PostConstruct
    protected void init() {
        byte[] keyBytes = jwtProperty.getSecret().getBytes();
        key = new SecretKeySpec(keyBytes, "HmacSHA512");
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String createToken(Authentication authentication) {
        String username = authentication.getName();
        Instant expiryDate = Instant.now().plusMillis(jwtProperty.getExpiration());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        JwtBuilder builder = Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expiryDate));

        if (!authorities.isEmpty()) {
            builder.claim("roles",
                    authorities.stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(joining(",")));
        }

        return builder.signWith(key).compact();
    }

    public UUID getUserIdFromJWT(String token) {
        Jws<Claims> jwsClaims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

        return UUID.fromString(jwsClaims.getPayload().getSubject());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Object authoritiesClaim = claims.get("roles");

        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Long getExpiryDuration() {
        return jwtProperty.getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}

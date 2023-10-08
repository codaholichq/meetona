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
    private final JwtSetting jwtSetting;

    public TokenProvider(JwtSetting jwtSetting) {
        this.jwtSetting = jwtSetting;
    }

    @PostConstruct
    protected void init() {
        byte[] keyBytes = jwtSetting.getSecret().getBytes();
        key = new SecretKeySpec(keyBytes, "HmacSHA512");
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String createToken(Authentication authentication) {
//        User userPrincipal = (User) authentication.getPrincipal();
        String username = authentication.getName();
        Instant expiryDate = Instant.now().plusMillis(jwtSetting.getExpiration());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Claims claims = Jwts.claims().setSubject(username);
        if (!authorities.isEmpty()) {
            claims.put("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
        }
        return Jwts.builder()
//                .setSubject((userPrincipal.getUsername()))
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(key)
                .compact();
    }

    public UUID getUserIdFromJWT(String token) {
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return UUID.fromString(jwsClaims.getBody().getSubject());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object authoritiesClaim = claims.get("roles");

        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Long getExpiryDuration() {
        return jwtSetting.getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
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

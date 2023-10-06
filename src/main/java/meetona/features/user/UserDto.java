package meetona.features.user;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        List<String> roles,
        UUID memberId,
        String accessToken
) implements Serializable {
    public UserDto setAccessToken(String accessToken) {
        return new UserDto(id, username, email, roles, memberId, accessToken);
    }
}

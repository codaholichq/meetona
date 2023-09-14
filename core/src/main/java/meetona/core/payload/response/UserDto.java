package meetona.core.payload.response;

import meetona.core.enums.UserRole;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String firstname,
        String lastname,
        String username,
        String email,
        List<UserRole> roles,
        String accessToken
//        UUID unitId
) implements Serializable {
    public UserDto setAccessToken(String accessToken) {
        return new UserDto(id, firstname, lastname, username, email, roles, accessToken);
    }
}

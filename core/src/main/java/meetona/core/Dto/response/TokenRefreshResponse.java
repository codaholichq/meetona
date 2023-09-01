package meetona.data.Dto.response;

import lombok.Data;

@Data
public class TokenRefreshResponse {
    private final String token;
    private final String refreshToken;
    private final String tokenType = "Bearer";

    public TokenRefreshResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}

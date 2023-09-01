package meetona.core.Dto.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record TokenRefreshDto(

    @NotBlank(message = "Refresh token cannot be blank")
    String refreshToken

) implements Serializable {}

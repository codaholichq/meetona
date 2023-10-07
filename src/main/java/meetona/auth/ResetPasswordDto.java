package meetona.auth;

import jakarta.validation.constraints.NotBlank;
import meetona.shared.validation.annotation.MatchPassword;

import java.io.Serializable;

@MatchPassword
public record ResetPasswordDto(

    @NotBlank(message = "Password cannot be blank")
    String password,

    @NotBlank(message = "Confirm Password cannot be blank")
    String confirmPassword,

    @NotBlank(message = "Token has to be supplied along with a password reset request")
    String token
) implements Serializable {}

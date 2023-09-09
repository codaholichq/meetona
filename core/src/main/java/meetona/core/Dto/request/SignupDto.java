package meetona.core.Dto.request;

import jakarta.validation.constraints.NotEmpty;
import meetona.core.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public record SignupDto(

    @NotBlank(message = "first name cannot be blank")
    String firstname,

    @NotBlank(message = "last name cannot be blank")
    String lastname,

    @NotBlank(message = "Username cannot be blank")
    String username,

    @Email(message = "Enter a valid email address")
    String email,

    @NotBlank(message = "Password cannot be blank")
    String password,

    @NotEmpty(message = "Role must note be blank")
    Set<Role> roles,

//    @NotNull(message = "Role must note be blank")
    UUID unitId

) implements Serializable {}

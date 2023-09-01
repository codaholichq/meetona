package meetona.data.Dto.request;

import meetona.core.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Set;

public record SignupDto(

    @NotBlank(message = "first name cannot be blank")
    String firstname,

    @NotBlank(message = "last name cannot be blank")
    String lastname,

    @NotBlank(message = "Username cannot be blank")
    String username,

    @Email
    String email,

    @NotBlank(message = "Password cannot be blank")
    String password,

    @NotBlank(message = "Role must note be blank")
    Set<Role> roles,

    @NotBlank(message = "Role must note be blank")
    long unitId

) implements Serializable {}

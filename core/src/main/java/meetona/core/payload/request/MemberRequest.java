package meetona.core.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import meetona.core.enums.Gender;
import meetona.core.enums.MaritalStatus;

import java.io.Serializable;
import java.util.UUID;

public record MemberRequest(

    @NotBlank(message = "first name cannot be blank")
    String firstName,

    @NotBlank(message = "last name cannot be blank")
    String middleName,

    @NotBlank(message = "last name cannot be blank")
    String lastName,

    @NotBlank(message = "Gender cannot be blank")
    Gender gender,

    @NotBlank(message = "Marital Status cannot be blank")
    MaritalStatus maritalStatus,

    @Email(message = "Enter a valid email address")
    String email,

    @NotNull(message = "Unit must note be blank")
    UUID unitId

) implements Serializable {}

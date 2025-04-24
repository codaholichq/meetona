package meetona.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public record MemberRequest(

    @NotBlank(message = "first name cannot be blank")
    String firstName,

    @NotBlank(message = "last name cannot be blank")
    String middleName,

    @NotBlank(message = "last name cannot be blank")
    String lastName,

    @NotNull(message = "Gender cannot be blank")
    Gender gender,

    @Email(message = "Enter a valid email address")
    String email,

    @NotBlank(message = "Enter a valid phone number")
    String phoneNumber,

    @NotNull(message = "BirthDate cannot be blank")
    LocalDate birthDate,

    @NotNull(message = "Marital Status cannot be blank")
    MaritalStatus maritalStatus,

    LocalDate MarriageDate,

    @NotNull(message = "Unit must note be blank")
    UUID cellId,

    UUID departmentId

) implements Serializable {}

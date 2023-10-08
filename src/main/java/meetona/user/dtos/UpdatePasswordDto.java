package meetona.user.dtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;


public record UpdatePasswordDto(

    @NotBlank(message = "Old password must not be blank")
    String oldPassword,

    @NotBlank(message = "New password must not be blank")
    String newPassword

) implements Serializable {}

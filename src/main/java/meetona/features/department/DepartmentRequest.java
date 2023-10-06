package meetona.features.department;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record DepartmentRequest(

    @NotBlank(message = "first name cannot be blank")
    String name,

    @NotBlank(message = "last name cannot be blank")
    String lead

) implements Serializable {}

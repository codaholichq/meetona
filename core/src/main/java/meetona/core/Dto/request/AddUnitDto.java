package meetona.core.Dto.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record AddUnitDto(
        @NotBlank(message = "unit name cannot be blank")
        String name,

        @NotBlank(message = "unit location cannot be blank")
        String location
) implements Serializable { }

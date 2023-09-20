package meetona.core.payload.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UnitRequest(
        @NotBlank(message = "unit name cannot be blank")
        String name,

        @NotBlank(message = "unit location cannot be blank")
        String address
) implements Serializable { }

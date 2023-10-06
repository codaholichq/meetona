package meetona.core.payload.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record UnitRequest(
        @NotBlank(message = "Unit name cannot be blank")
        String name,

        @NotBlank(message = "Unit address cannot be blank")
        String address
) implements Serializable { }

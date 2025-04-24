package meetona.cell;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record CellRequest(
        @NotBlank(message = "Cell name cannot be blank")
        String name,

        @NotBlank(message = "Cell address cannot be blank")
        String address
) implements Serializable { }

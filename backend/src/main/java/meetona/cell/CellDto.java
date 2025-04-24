package meetona.cell;

import java.io.Serializable;
import java.util.UUID;

public record CellDto(
        UUID id,
        String name,
        String address
) implements Serializable { }

package meetona.core.payload.response;

import java.io.Serializable;
import java.util.UUID;

public record UnitDto(
        UUID id,
        String name,
        String address
) implements Serializable { }

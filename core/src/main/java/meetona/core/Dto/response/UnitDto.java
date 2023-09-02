package meetona.core.Dto.response;

import java.io.Serializable;
import java.util.UUID;

public record UnitDto(
        UUID id,
        String name,
        String location
) implements Serializable { }

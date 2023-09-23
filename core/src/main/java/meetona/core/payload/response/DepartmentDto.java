package meetona.core.payload.response;

import java.io.Serializable;
import java.util.UUID;

public record DepartmentDto(
        UUID id,
        String name,
        String lead
) implements Serializable { }

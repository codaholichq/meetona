package meetona.core.payload.response;

import java.io.Serializable;
import java.util.UUID;

public record MeetingDto(
        UUID id,
        int memberCount,
        String prayerPoint,
        UUID unitId
) implements Serializable { }

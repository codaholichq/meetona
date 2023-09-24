package meetona.core.payload.response;

import java.io.Serializable;
import java.util.UUID;

public record MeetingDto(
        int memberCount,
        String prayerPoint,
        UUID unitId
) implements Serializable { }

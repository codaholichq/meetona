package meetona.meeting;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

public record MeetingRequest(
        @NotNull
        int memberCount,

        @NotEmpty
        String prayerPoint,

        @NotNull(message = "Unit must note be blank")
        UUID cellId
) implements Serializable { }

package meetona.core.payload.response;

import meetona.core.enums.Gender;
import meetona.core.enums.MaritalStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public record MemberDto(
        UUID id,
        String firstName,
        String middleName,
        String lastName,
        Gender gender,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        LocalDate marriageDate,
        MaritalStatus maritalStatus,
        UUID unitId
) implements Serializable { }

package meetona.member;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

public record MemberDto(
        UUID id,
        String firstName,
        String middleName,
        String lastName,
        Gender gender,
        String email,
        String phoneNumber,
        Date birthDate,
        Date marriageDate,
        MaritalStatus maritalStatus,
        String unit
) implements Serializable { }

package meetona.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import meetona.core.common.BaseEntity;
import meetona.core.enums.Gender;
import meetona.core.enums.MaritalStatus;

import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "members")
@EqualsAndHashCode(callSuper = false, exclude = {"unit", "department"})
public class Member extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String middleName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private Gender gender;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 11)
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate marriageDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 7)
    private MaritalStatus maritalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
}

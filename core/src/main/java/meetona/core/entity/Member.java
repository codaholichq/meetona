package meetona.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import meetona.core.common.BaseEntity;
import meetona.core.enums.Gender;
import meetona.core.enums.MaritalStatus;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
@EqualsAndHashCode(callSuper = false)
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
    private String email;

    @Column(nullable = false, length = 11)
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

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Meeting> meetings;
}

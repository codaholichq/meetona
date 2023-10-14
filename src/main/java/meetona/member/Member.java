package meetona.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import meetona.shared.entity.BaseEntity;
import meetona.department.Department;
import meetona.unit.Unit;
import meetona.user.User;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Data
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
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
    @Column(nullable = false, length = 6)
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
}

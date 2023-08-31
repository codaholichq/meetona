package meetona.core.entity;

import meetona.core.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Transient;
import meetona.core.common.BaseEntity;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

    private String firstname;
    private String lastname;
    protected String username;
    private String email;
    protected String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    protected Set<Role> roles = new HashSet<>();

    @Column(name = "unit_id")
    private long unitId;

    @Transient
    private Units units;

    private Boolean isEmailVerified;

}
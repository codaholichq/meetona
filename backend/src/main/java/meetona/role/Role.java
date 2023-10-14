package meetona.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import meetona.shared.entity.BaseEntity;
import meetona.user.User;
import meetona.shared.enums.AppRole;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;

@Data
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "role")
@Table(name = "roles")
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private AppRole name;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}

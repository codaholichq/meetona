package meetona.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import meetona.core.common.BaseEntity;
import meetona.core.enums.AppRole;

import java.util.Collection;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "roles")
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private AppRole name;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}

package meetona.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import meetona.core.common.BaseEntity;

import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "units")
@EqualsAndHashCode(callSuper = false)
public class Unit extends BaseEntity {

    private String name;
    private String location;

    @Transient
    @Builder.Default
    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();
}


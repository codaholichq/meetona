package meetona.core.entity;

import jakarta.persistence.*;
import lombok.*;
import meetona.core.common.BaseEntity;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "units")
@EqualsAndHashCode(callSuper = false)
public class Unit extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Transient
    @Builder.Default
    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    @Transient
    @Builder.Default
    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Member> members = new HashSet<>();
}


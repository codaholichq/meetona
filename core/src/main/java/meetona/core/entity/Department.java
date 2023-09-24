package meetona.core.entity;

import jakarta.persistence.*;
import lombok.*;
import meetona.core.common.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "departments")
@EqualsAndHashCode(callSuper = false)
public class Department extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String lead;

    @Transient
    @Builder.Default
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Member> members = new HashSet<>();
}

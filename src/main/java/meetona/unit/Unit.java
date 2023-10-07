package meetona.unit;

import jakarta.persistence.*;
import lombok.*;
import meetona.common.BaseEntity;
import meetona.member.Member;

import java.util.Set;


@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "units")
@EqualsAndHashCode(callSuper = false)
public class Unit extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Member> members;
}


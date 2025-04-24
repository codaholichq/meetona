package meetona.cell;

import jakarta.persistence.*;
import lombok.*;
import meetona.shared.entity.BaseEntity;
import meetona.member.Member;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;


@Data
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cell")
@Table(name = "cells")
@EqualsAndHashCode(callSuper = false)
public class Cell extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "cell", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Member> members;
}


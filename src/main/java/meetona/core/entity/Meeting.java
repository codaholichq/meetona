package meetona.core.entity;

import jakarta.persistence.*;
import lombok.*;
import meetona.core.common.BaseEntity;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "meetings")
@EqualsAndHashCode(callSuper = false)
public class Meeting extends BaseEntity {

    @Column(nullable = false)
    private int memberCount;

    @Column(nullable = false)
    private String prayerPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit;
}

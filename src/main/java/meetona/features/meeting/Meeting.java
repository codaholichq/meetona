package meetona.features.meeting;

import jakarta.persistence.*;
import lombok.*;
import meetona.features.common.BaseEntity;
import meetona.features.unit.Unit;

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

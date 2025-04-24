package meetona.meeting;

import jakarta.persistence.*;
import lombok.*;
import meetona.shared.entity.BaseEntity;
import meetona.cell.Cell;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "meeting")
@Table(name = "meetings")
@EqualsAndHashCode(callSuper = false)
public class Meeting extends BaseEntity {

    @Column(nullable = false)
    private int memberCount;

    @Column(nullable = false)
    private String prayerPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cell_id", referencedColumnName = "id")
    private Cell cell;
}

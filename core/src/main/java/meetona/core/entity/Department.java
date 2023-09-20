package meetona.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import meetona.core.common.BaseEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departments")
@EqualsAndHashCode(callSuper = false)
public class Department extends BaseEntity {

    private String name;
    private String lead;
}

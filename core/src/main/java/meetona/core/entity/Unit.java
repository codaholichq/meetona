package meetona.core.entity;

import meetona.core.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "units")
@EqualsAndHashCode(callSuper = false)
public class Units extends BaseEntity {

    private String name;
    private String location;
}


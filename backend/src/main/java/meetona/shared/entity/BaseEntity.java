package meetona.shared.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5L;

    @Id
    @GeneratedValue
    private UUID id;

    @JsonIgnore
    @CreatedBy
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created_dt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdDate;

    @JsonIgnore
    @LastModifiedBy
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "updated_dt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant updatedDate;

    @JsonIgnore
    @Version
    private Integer version;
}

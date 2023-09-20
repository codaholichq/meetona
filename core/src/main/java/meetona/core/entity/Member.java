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
@Table(name = "members")
@EqualsAndHashCode(callSuper = false)
public class Member extends BaseEntity {
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;
    private String BirthDay;
    private String MarriageDate;
    private String MaritalStatus;
}

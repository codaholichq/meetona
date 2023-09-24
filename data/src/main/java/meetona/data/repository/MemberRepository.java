package meetona.data.repository;

import meetona.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Boolean existsByPhoneNumber(String name);
    Boolean existsByEmail(String email);
}

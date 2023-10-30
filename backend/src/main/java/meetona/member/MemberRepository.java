package meetona.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Boolean existsByPhoneNumber(String name);
    Optional<Member> findByEmail(String email);
    Boolean existsByEmail(String email);
}

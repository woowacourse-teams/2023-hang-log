package hanglog.member.domain.repository;

import hanglog.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialLoginId(String socialLoginId);

    boolean existsByNickname(String nickname);
}

package hanglog.member.repository;

import hanglog.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsBySocialLoginId(String socialLoginId);
}

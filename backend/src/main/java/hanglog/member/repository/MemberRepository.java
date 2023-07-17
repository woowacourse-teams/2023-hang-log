package hanglog.member.repository;

import hanglog.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsBySocialLoginId(String socialLoginId);
}

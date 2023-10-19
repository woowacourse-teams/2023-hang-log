package hanglog.member.domain.repository;

import hanglog.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialLoginId(String socialLoginId);

    boolean existsByNickname(String nickname);

    @Modifying
    @Query("""
            UPDATE Member member
            SET member.status = 'DELETED'
            WHERE member.id = :memberId
            """)
    void deleteByMemberId(@Param("memberId") final Long memberId);
}

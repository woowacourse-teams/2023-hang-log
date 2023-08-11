package hanglog.member.domain.repository;

import hanglog.member.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Long findMemberIdByToken(final String token);

    void deleteByMemberId(final Long memberId);
}

package hanglog.member.domain.repository;

import hanglog.member.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    public void deleteByMemberId(final Long memberId);
}

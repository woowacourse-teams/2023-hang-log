package hanglog.auth.domain.repository;

import hanglog.auth.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(final String token);
    
    boolean existsByToken(final String token);

    void deleteByMemberId(final Long memberId);
}

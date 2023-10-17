package hanglog.oauth.domain.repository;

import hanglog.oauth.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(final String token);

    boolean existsByToken(final String token);

    @Modifying
    @Query("""
            DELETE FROM RefreshToken refreshToken
            WHERE refreshToken.memberId = :memberId
            """)
    void deleteByMemberId(@Param("memberId") final Long memberId);
}

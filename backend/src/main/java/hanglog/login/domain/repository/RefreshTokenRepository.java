package hanglog.login.domain.repository;

import hanglog.login.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(final String token);

    boolean existsByToken(final String token);
}

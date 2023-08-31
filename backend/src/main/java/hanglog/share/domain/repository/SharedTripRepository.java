package hanglog.share.domain.repository;

import hanglog.share.domain.SharedTrip;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedTripRepository extends JpaRepository<SharedTrip, Long> {

    Optional<SharedTrip> findBySharedCode(final String sharedCode);
}

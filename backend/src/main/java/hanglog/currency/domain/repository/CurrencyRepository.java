package hanglog.currency.domain.repository;

import hanglog.currency.domain.Currency;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findTopByDateLessThanEqualOrderByDateDesc(final LocalDate date);

    Optional<Currency> findTopByOrderByDateAsc();

    boolean existsByDate(LocalDate date);
}

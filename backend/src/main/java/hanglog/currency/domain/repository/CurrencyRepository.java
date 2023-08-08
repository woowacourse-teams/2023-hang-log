package hanglog.currency.domain.repository;

import hanglog.currency.domain.Currency;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findTopByDateLessThanEqualOrderByDateDesc(final LocalDate date);

    Optional<Currency> findTopByOrderByDateAsc();

    Currency findByDate(LocalDate date);

    boolean existsByDate(LocalDate date);
}

package hanglog.expense.domain.repository;

import hanglog.expense.domain.Currency;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findCurrenciesBySearchDate(final LocalDate searchDate);
}

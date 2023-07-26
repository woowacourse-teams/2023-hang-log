package hanglog.expense.repository;

import hanglog.expense.Currencies;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrenciesRepository extends JpaRepository<Currencies, Long> {

    Optional<Currencies> findCurrenciesBySearchDate(final LocalDate searchDate);
}

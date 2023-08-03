package hanglog.expense.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import hanglog.expense.domain.Currency;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CurrencyRepositoryTest {

    @Autowired
    CurrencyRepository currencyRepository;

    @DisplayName("입력한 날짜의 가장 최근 환율 정보를 가져온다.")
    @Test
    void getLatestCurrency() {
        // given
        final Currency expect = saveCurrency(2023, 1, 5);
        saveCurrency(2021, 1, 1);
        saveCurrency(2021, 1, 2);
        saveCurrency(2022, 2, 1);
        saveCurrency(2023, 1, 4);

        // when
        final Currency actual = currencyRepository.findTopByDateLessThanEqualOrderByDateDesc(LocalDate.of(2023, 1, 30))
                .get();

        //then
        assertThat(actual.getId()).isEqualTo(expect.getId());
    }

    @DisplayName("가장 오래된 환율 정보를 가져온다.")
    @Test
    void getOldestCurrency() {
        // given
        final Currency expect = saveCurrency(2023, 1, 1);
        saveCurrency(2023, 1, 2);
        saveCurrency(2023, 1, 3);
        saveCurrency(2023, 1, 4);
        saveCurrency(2023, 1, 5);

        // when
        final Currency actual = currencyRepository.findTopByOrderByDateAsc().get();

        //then
        assertThat(actual).isEqualTo(expect);
    }

    private Currency saveCurrency(final int year, final int month, final int date) {
        return currencyRepository.save(
                new Currency(null, LocalDate.of(year, month, date), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        );
    }
}

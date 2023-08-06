package hanglog.currency.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.expense.domain.Currency;
import hanglog.expense.service.CurrencyService;
import hanglog.global.exception.InvalidDomainException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @DisplayName("금일 환율 정보를 저장한다.")
    @Test
    void saveCurrency() {
        // given
        final LocalDate date = LocalDate.of(2023, 8, 3);

        // when
        final Currency actual = currencyService.saveDailyCurrency(date);

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(actual).extracting("usd").isNotNull();
                    softly.assertThat(actual).extracting("eur").isNotNull();
                    softly.assertThat(actual).extracting("gbp").isNotNull();
                    softly.assertThat(actual).extracting("jpy").isNotNull();
                    softly.assertThat(actual).extracting("cny").isNotNull();
                    softly.assertThat(actual).extracting("chf").isNotNull();
                    softly.assertThat(actual).extracting("sgd").isNotNull();
                    softly.assertThat(actual).extracting("thb").isNotNull();
                    softly.assertThat(actual).extracting("hkd").isNotNull();
                    softly.assertThat(actual).extracting("krw").isNotNull();
                }
        );
    }

    @ParameterizedTest(name = "주말에는 환율 정보를 받아오지 못한다.")
    @ValueSource(ints = {5, 6})
    void failSaveCurrencyAtWeekend(final int date) {
        // given
        final LocalDate weekend = LocalDate.of(2023, 8, date);

        // when & then
        assertThatThrownBy(() -> currencyService.saveDailyCurrency(weekend))
                .isInstanceOf(InvalidDomainException.class)
                .extracting("code").isEqualTo(3006);
    }
}

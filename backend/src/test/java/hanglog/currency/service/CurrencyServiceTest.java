package hanglog.currency.service;

import static hanglog.global.exception.ExceptionCode.INVALID_CURRENCY_DATE_WHEN_WEEKEND;
import static hanglog.global.exception.ExceptionCode.INVALID_DATE_ALREADY_EXIST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.currency.domain.Currency;
import hanglog.currency.domain.repository.CurrencyRepository;
import hanglog.global.exception.BadRequestException;
import hanglog.global.exception.InvalidCurrencyDateException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CurrencyServiceTest {

    private final CurrencyService currencyService;
    private final CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyServiceTest(
            final CurrencyRepository currencyRepository,
            @Value("${currency.auth-key}") final String authKey
    ) {
        this.currencyService = new CurrencyService(currencyRepository, authKey);
        this.currencyRepository = currencyRepository;
    }

    @DisplayName("금일 환율 정보를 저장한다.")
    @Test
    void saveCurrency() {
        // given
        final LocalDate date = LocalDate.of(2023, 8, 3);

        // when
        currencyService.saveDailyCurrency(date);
        final Currency actual = currencyRepository.findTopByOrderByDateAsc().get();

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
    void saveCurrency_FailAtWeekend(final int date) {
        // given
        final LocalDate weekend = LocalDate.of(2023, 8, date);

        // when & then
        assertThatThrownBy(() -> currencyService.saveDailyCurrency(weekend))
                .isInstanceOf(InvalidCurrencyDateException.class)
                .hasMessage(INVALID_CURRENCY_DATE_WHEN_WEEKEND.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_CURRENCY_DATE_WHEN_WEEKEND.getCode());
    }

    @DisplayName("이미 정보가 존재하는 날짜의 데이터는 받아오지 못한다.")
    @Test
    void saveCurrency_FailAtExistDate() {
        // given
        final LocalDate date = LocalDate.of(2023, 8, 3);
        currencyService.saveDailyCurrency(date);

        // when & then
        assertThatThrownBy(() -> currencyService.saveDailyCurrency(date))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_DATE_ALREADY_EXIST.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_DATE_ALREADY_EXIST.getCode());
    }
}

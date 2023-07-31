package hanglog.currency.service;


import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hanglog.expense.domain.Currency;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @DisplayName("금일 환율 정보를 저장한다.")
    @Test
    void saveCurrency() {
        // when
        final Currency actual = currencyService.saveTodayCurrency();

        // then
        assertSoftly(
                softly -> {
                    softly.assertThat(actual).extracting("usd").isNotNull();
                    softly.assertThat(actual).extracting("eur").isNotNull();
                    softly.assertThat(actual).extracting("gbp").isNotNull();
                    softly.assertThat(actual).extracting("jpy").isNotNull();
                    softly.assertThat(actual).extracting("cnh").isNotNull();
                    softly.assertThat(actual).extracting("chf").isNotNull();
                    softly.assertThat(actual).extracting("sgd").isNotNull();
                    softly.assertThat(actual).extracting("thb").isNotNull();
                    softly.assertThat(actual).extracting("hkd").isNotNull();
                    softly.assertThat(actual).extracting("krw").isNotNull();
                }
        );
    }
}

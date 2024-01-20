package hanglog.currency.service;

import static hanglog.currency.fixture.CurrencyFixture.CURRENCY_1;
import static hanglog.currency.fixture.CurrencyFixture.CURRENCY_2;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import hanglog.currency.domain.repository.CurrencyRepository;
import hanglog.currency.dto.CurrencyListResponse;
import hanglog.currency.dto.CurrencyResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepository currencyRepository;

    @DisplayName("환율 리스트를 페이지로 조회한다.")
    @Test
    void getCurrenciesByPage() {
        // given
        final Pageable pageable = PageRequest.of(0, 10);

        given(currencyRepository.findBy(any())).willReturn(List.of(CURRENCY_1, CURRENCY_2));
        given(currencyRepository.count()).willReturn(2L);

        // when
        final CurrencyListResponse actual = currencyService.getCurrenciesByPage(pageable);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.getCurrencies()).size().isEqualTo(2);
            softly.assertThat(actual.getCurrencies()).usingRecursiveComparison()
                    .isEqualTo(List.of(CurrencyResponse.of(CURRENCY_1), CurrencyResponse.of(CURRENCY_2)));
            softly.assertThat(actual.getLastPageIndex()).isEqualTo(1L);
        });
    }
}

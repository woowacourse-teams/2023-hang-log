package hanglog.currency.service;

import static hanglog.currency.fixture.CurrencyFixture.CURRENCY_1;
import static hanglog.currency.fixture.CurrencyFixture.CURRENCY_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import hanglog.currency.domain.Currency;
import hanglog.currency.domain.repository.CurrencyRepository;
import hanglog.currency.dto.request.CurrencyRequest;
import hanglog.currency.dto.response.CurrencyListResponse;
import hanglog.currency.dto.response.CurrencyResponse;
import hanglog.global.exception.BadRequestException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

        given(currencyRepository.findAllBy(any())).willReturn(List.of(CURRENCY_1, CURRENCY_2));
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

    @DisplayName("새로운 환율 정보를 추가한다.")
    @Test
    void save() {
        // given
        final CurrencyRequest currencyRequest = new CurrencyRequest(
                CURRENCY_1.getDate(),
                CURRENCY_1.getUsd(),
                CURRENCY_1.getEur(),
                CURRENCY_1.getGbp(),
                CURRENCY_1.getJpy(),
                CURRENCY_1.getCny(),
                CURRENCY_1.getChf(),
                CURRENCY_1.getSgd(),
                CURRENCY_1.getThb(),
                CURRENCY_1.getHkd(),
                CURRENCY_1.getKrw()
        );

        given(currencyRepository.existsByDate(any())).willReturn(false);
        given(currencyRepository.save(any(Currency.class))).willReturn(CURRENCY_1);

        // when
        final Long actualId = currencyService.save(currencyRequest);

        // then
        assertThat(actualId).isEqualTo(CURRENCY_1.getId());
    }

    @DisplayName("중복된 날짜의 환율을 추가할 수 없다.")
    @Test
    void save_DuplicateDateFail() {
        // given
        final CurrencyRequest currencyRequest = new CurrencyRequest(
                CURRENCY_1.getDate(),
                CURRENCY_1.getUsd(),
                CURRENCY_1.getEur(),
                CURRENCY_1.getGbp(),
                CURRENCY_1.getJpy(),
                CURRENCY_1.getCny(),
                CURRENCY_1.getChf(),
                CURRENCY_1.getSgd(),
                CURRENCY_1.getThb(),
                CURRENCY_1.getHkd(),
                CURRENCY_1.getKrw()
        );

        given(currencyRepository.existsByDate(any())).willReturn(true);

        // when &then
        assertThatThrownBy(() -> currencyService.save(currencyRequest))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("환율 정보를 수정한다.")
    @Test
    void update() {
        // given
        final CurrencyRequest currencyRequest = new CurrencyRequest(
                CURRENCY_1.getDate(),
                99.99,
                CURRENCY_1.getEur(),
                CURRENCY_1.getGbp(),
                CURRENCY_1.getJpy(),
                CURRENCY_1.getCny(),
                CURRENCY_1.getChf(),
                CURRENCY_1.getSgd(),
                CURRENCY_1.getThb(),
                CURRENCY_1.getHkd(),
                CURRENCY_1.getKrw()
        );

        given(currencyRepository.findById(anyLong())).willReturn(Optional.of(CURRENCY_1));

        // when & then
        assertDoesNotThrow(() -> currencyService.update(CURRENCY_1.getId(), currencyRequest));
    }

    @DisplayName("수정한 환율 정보의 날짜가 다른 환율 정보와 중복되면 예외가 발생한다.")
    @Test
    void update_DuplicateFail() {
        // given
        final CurrencyRequest currencyRequest = new CurrencyRequest(
                LocalDate.of(9999, 1, 1),
                99.99,
                CURRENCY_1.getEur(),
                CURRENCY_1.getGbp(),
                CURRENCY_1.getJpy(),
                CURRENCY_1.getCny(),
                CURRENCY_1.getChf(),
                CURRENCY_1.getSgd(),
                CURRENCY_1.getThb(),
                CURRENCY_1.getHkd(),
                CURRENCY_1.getKrw()
        );

        given(currencyRepository.findById(anyLong())).willReturn(Optional.of(CURRENCY_1));
        given(currencyRepository.existsByDate(any())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> currencyService.update(CURRENCY_1.getId(), currencyRequest))
                .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("ID에 해당하는 환율 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void update_NotFoundFail() {
        // given
        final CurrencyRequest currencyRequest = new CurrencyRequest(
                CURRENCY_1.getDate(),
                99.99,
                CURRENCY_1.getEur(),
                CURRENCY_1.getGbp(),
                CURRENCY_1.getJpy(),
                CURRENCY_1.getCny(),
                CURRENCY_1.getChf(),
                CURRENCY_1.getSgd(),
                CURRENCY_1.getThb(),
                CURRENCY_1.getHkd(),
                CURRENCY_1.getKrw()
        );

        given(currencyRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> currencyService.update(CURRENCY_1.getId(), currencyRequest))
                .isInstanceOf(BadRequestException.class);
    }
}

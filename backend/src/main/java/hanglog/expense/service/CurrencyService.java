package hanglog.expense.service;

import static hanglog.expense.domain.type.CurrencyType.CHF;
import static hanglog.expense.domain.type.CurrencyType.CNY;
import static hanglog.expense.domain.type.CurrencyType.EUR;
import static hanglog.expense.domain.type.CurrencyType.GBP;
import static hanglog.expense.domain.type.CurrencyType.HKD;
import static hanglog.expense.domain.type.CurrencyType.JPY;
import static hanglog.expense.domain.type.CurrencyType.KRW;
import static hanglog.expense.domain.type.CurrencyType.SGD;
import static hanglog.expense.domain.type.CurrencyType.THB;
import static hanglog.expense.domain.type.CurrencyType.USD;
import static hanglog.global.exception.ExceptionCode.INVALID_DATE_WHEN_WEEKEND;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CURRENCY_DATA;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import hanglog.expense.domain.Currency;
import hanglog.expense.domain.repository.CurrencyRepository;
import hanglog.expense.domain.type.CurrencyType;
import hanglog.expense.dto.response.CurrencyResponse;
import hanglog.global.exception.InvalidDomainException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Transactional
public class CurrencyService {

    private static final String CURRENCY_API_URI = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON";

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;
    private final String authKey;

    public CurrencyService(
            final CurrencyRepository currencyRepository,
            @Value("${currency.auth-key}") final String authKey
    ) {
        this.restTemplate = new RestTemplate();
        this.currencyRepository = currencyRepository;
        this.authKey = authKey;
    }

    public Currency saveDailyCurrency(final LocalDate date) {
        validateWeekend(date);
        final List<CurrencyResponse> currencyResponseList = getCurrencyList(date);
        final Map<CurrencyType, Double> currencyTypeRateMap = new EnumMap<>(CurrencyType.class);

        for (final CurrencyResponse currencyResponse : currencyResponseList) {
            final String currencyUnit = currencyResponse.getCurUnit().toLowerCase().replace("(100)", "");

            if (CurrencyType.provide(currencyUnit)) {
                currencyTypeRateMap.put(
                        CurrencyType.getMappedCurrencyType(currencyUnit),
                        Double.valueOf(currencyResponse.getDealBasR().replace(",", ""))
                );
            }
        }

        final Currency currency = getCurrency(date, currencyTypeRateMap);
        return currencyRepository.save(currency);
    }

    private void validateWeekend(final LocalDate date) {
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek.equals(SUNDAY) || dayOfWeek.equals(SATURDAY)) {
            throw new InvalidDomainException(INVALID_DATE_WHEN_WEEKEND);
        }
    }

    private List<CurrencyResponse> getCurrencyList(final LocalDate today) {
        final CurrencyResponse[] responses = Optional.ofNullable(
                restTemplate.getForObject(getCurrencyUrl(today), CurrencyResponse[].class)
        ).orElseThrow(() -> new InvalidDomainException(NOT_FOUND_CURRENCY_DATA));
        return Arrays.stream(responses)
                .toList();
    }

    private String getCurrencyUrl(final LocalDate today) {
        return UriComponentsBuilder.fromHttpUrl(CURRENCY_API_URI)
                .queryParam("authkey", authKey)
                .queryParam("searchdate", today.toString().replace("-", ""))
                .queryParam("data", "AP01")
                .toUriString();
    }

    private Currency getCurrency(final LocalDate today, final Map<CurrencyType, Double> currencyTypeRateMap) {
        return new Currency(
                today,
                currencyTypeRateMap.get(USD),
                currencyTypeRateMap.get(EUR),
                currencyTypeRateMap.get(GBP),
                currencyTypeRateMap.get(JPY),
                currencyTypeRateMap.get(CNY),
                currencyTypeRateMap.get(CHF),
                currencyTypeRateMap.get(SGD),
                currencyTypeRateMap.get(THB),
                currencyTypeRateMap.get(HKD),
                currencyTypeRateMap.get(KRW)
        );
    }
}

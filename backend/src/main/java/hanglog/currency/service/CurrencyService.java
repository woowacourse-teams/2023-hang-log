package hanglog.currency.service;

import static hanglog.currency.domain.type.CurrencyType.CHF;
import static hanglog.currency.domain.type.CurrencyType.CNY_FROM_API;
import static hanglog.currency.domain.type.CurrencyType.EUR;
import static hanglog.currency.domain.type.CurrencyType.GBP;
import static hanglog.currency.domain.type.CurrencyType.HKD;
import static hanglog.currency.domain.type.CurrencyType.JPY;
import static hanglog.currency.domain.type.CurrencyType.KRW;
import static hanglog.currency.domain.type.CurrencyType.SGD;
import static hanglog.currency.domain.type.CurrencyType.THB;
import static hanglog.currency.domain.type.CurrencyType.USD;
import static hanglog.global.exception.ExceptionCode.INVALID_CURRENCY_DATE_WHEN_WEEKEND;
import static hanglog.global.exception.ExceptionCode.INVALID_DATE_ALREADY_EXIST;
import static hanglog.global.exception.ExceptionCode.NOT_FOUND_CURRENCY_DATA;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import hanglog.currency.domain.Currency;
import hanglog.currency.domain.repository.CurrencyRepository;
import hanglog.currency.domain.type.CurrencyType;
import hanglog.currency.dto.request.CurrencyRequest;
import hanglog.currency.dto.response.CurrencyListResponse;
import hanglog.currency.dto.response.CurrencyResponse;
import hanglog.currency.dto.response.SingleCurrencyResponse;
import hanglog.global.exception.BadRequestException;
import hanglog.global.exception.InvalidCurrencyDateException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Transactional
public class CurrencyService {

    private static final String CURRENCY_API_URI = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON";
    private static final String NUMBER_SEPARATOR = ",";
    private static final String DATE_SEPARATOR = "-";

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

    @Scheduled(cron = "0 0 0 * * *")
    public void saveYesterdayCurrency() {
        try {
            final LocalDate yesterday = LocalDate.now().minusDays(1);
            saveDailyCurrency(yesterday);
        } catch (final InvalidCurrencyDateException ignored) {
        }
    }

    public void saveDailyCurrency(final LocalDate date) {
        validateDuplicateDate(date);

        validateWeekend(date);
        final List<SingleCurrencyResponse> singleCurrencyResponses = requestFilteredCurrencyResponses(date);
        final Map<CurrencyType, Double> rateOfCurrencyType = new EnumMap<>(CurrencyType.class);

        for (final SingleCurrencyResponse singleCurrencyResponse : singleCurrencyResponses) {
            rateOfCurrencyType.put(
                    CurrencyType.getMappedCurrencyType(singleCurrencyResponse.getCode()),
                    Double.valueOf(singleCurrencyResponse.getRate().replace(NUMBER_SEPARATOR, ""))
            );
        }

        final Currency currency = createCurrency(date, rateOfCurrencyType);
        currencyRepository.save(currency);
    }

    private void validateDuplicateDate(final LocalDate date) {
        if (currencyRepository.existsByDate(date)) {
            throw new BadRequestException(INVALID_DATE_ALREADY_EXIST);
        }
    }

    private void validateWeekend(final LocalDate date) {
        final DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek.equals(SUNDAY) || dayOfWeek.equals(SATURDAY)) {
            throw new InvalidCurrencyDateException(INVALID_CURRENCY_DATE_WHEN_WEEKEND);
        }
    }

    private List<SingleCurrencyResponse> requestFilteredCurrencyResponses(final LocalDate date) {
        final SingleCurrencyResponse[] responses = restTemplate.getForObject(
                createCurrencyUrl(date),
                SingleCurrencyResponse[].class
        );
        if (Objects.requireNonNull(responses).length == 0) {
            throw new InvalidCurrencyDateException(NOT_FOUND_CURRENCY_DATA);
        }
        return Arrays.stream(responses)
                .filter(response -> CurrencyType.isProvided(response.getCode()))
                .toList();
    }

    private String createCurrencyUrl(final LocalDate date) {
        return UriComponentsBuilder.fromHttpUrl(CURRENCY_API_URI)
                .queryParam("authkey", authKey)
                .queryParam("searchdate", date.toString().replace(DATE_SEPARATOR, ""))
                .queryParam("data", "AP01")
                .toUriString();
    }

    private Currency createCurrency(final LocalDate date, final Map<CurrencyType, Double> currencyTypeRateMap) {
        return new Currency(
                date,
                currencyTypeRateMap.get(USD),
                currencyTypeRateMap.get(EUR),
                currencyTypeRateMap.get(GBP),
                currencyTypeRateMap.get(JPY),
                currencyTypeRateMap.get(CNY_FROM_API),
                currencyTypeRateMap.get(CHF),
                currencyTypeRateMap.get(SGD),
                currencyTypeRateMap.get(THB),
                currencyTypeRateMap.get(HKD),
                currencyTypeRateMap.get(KRW)
        );
    }

    @Transactional(readOnly = true)
    public CurrencyListResponse getCurrenciesByPage(final Pageable pageable) {
        final List<Currency> currencies = currencyRepository.findAllBy(pageable.previousOrFirst());
        final List<CurrencyResponse> currencyResponses = currencies.stream()
                .map(CurrencyResponse::of)
                .toList();
        final Long lastPageIndex = getLastPageIndex(pageable.getPageSize());

        return new CurrencyListResponse(currencyResponses, lastPageIndex);
    }

    private Long getLastPageIndex(final int pageSize) {
        final long totalCount = currencyRepository.count();
        final long lastPageIndex = totalCount / pageSize;
        if (totalCount % pageSize == 0) {
            return lastPageIndex;
        }
        return lastPageIndex + 1;
    }

    public Long save(final CurrencyRequest currencyRequest) {
        validateDuplicateDate(currencyRequest.getDate());

        return currencyRepository.save(Currency.of(currencyRequest)).getId();
    }

    public void update(final Long id, final CurrencyRequest currencyRequest) {
        final Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CURRENCY_DATA));

        validateDuplicateDate(currency.getDate(), currencyRequest.getDate());

        currency.update(currencyRequest);
    }

    private void validateDuplicateDate(final LocalDate date, final LocalDate newDate) {
        if (!date.equals(newDate)) {
            validateDuplicateDate(newDate);
        }
    }
}

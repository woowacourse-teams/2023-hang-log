package hanglog.expense.dto.response;


import hanglog.city.dto.response.CityResponse;
import hanglog.expense.domain.Currency;
import hanglog.trip.domain.Trip;
import hanglog.trip.domain.TripCity;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TripExpenseResponse {

    private final Long id;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<CityResponse> cities;
    private final int totalAmount;
    private final List<CategoryExpenseResponse> categories;
    private final ExchangeRateResponse exchangeRate;
    private final List<DayLogExpenseResponse> dayLogs;

    public static TripExpenseResponse of(
            final Trip trip,
            final int totalAmount,
            final List<TripCity> cities,
            final List<CategoryExpenseResponse> categories,
            final Currency currency,
            final List<DayLogExpenseResponse> dayLogs
    ) {
        final List<CityResponse> cityExpenseResponses = cities.stream()
                .map(tripCity -> CityResponse.of(tripCity.getCity()))
                .toList();

        return new TripExpenseResponse(
                trip.getId(),
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                cityExpenseResponses,
                totalAmount,
                categories,
                ExchangeRateResponse.of(currency),
                dayLogs
        );
    }
}

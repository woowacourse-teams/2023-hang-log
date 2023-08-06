package hanglog.expense.dto.response;


import hanglog.city.dto.response.CityResponse;
import hanglog.expense.domain.CategoryExpense;
import hanglog.expense.domain.Currency;
import hanglog.expense.domain.DayLogExpense;
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
            final List<TripCity> tripCities,
            final List<CategoryExpense> categoryExpenses,
            final Currency currency,
            final List<DayLogExpense> dayLogExpenses
    ) {
        final List<CityResponse> cityExpenseResponses = tripCities.stream()
                .map(tripCity -> CityResponse.of(tripCity.getCity()))
                .toList();

        final List<CategoryExpenseResponse> categoryExpenseResponses = categoryExpenses.stream()
                .map(CategoryExpenseResponse::of)
                .toList();

        final List<DayLogExpenseResponse> dayLogExpenseResponses = dayLogExpenses.stream()
                .map(DayLogExpenseResponse::of)
                .toList();

        return new TripExpenseResponse(
                trip.getId(),
                trip.getTitle(),
                trip.getStartDate(),
                trip.getEndDate(),
                cityExpenseResponses,
                totalAmount,
                categoryExpenseResponses,
                ExchangeRateResponse.of(currency),
                dayLogExpenseResponses
        );
    }
}

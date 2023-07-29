package hanglog.expense.service;


import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;
import static hanglog.expense.fixture.CityFixture.LONDON;
import static hanglog.expense.fixture.CityFixture.TOKYO;
import static hanglog.expense.fixture.CurrencyFixture.DEFAULT_CURRENCY;
import static hanglog.trip.fixture.DayLogFixture.EXPENSE_JAPAN_DAYLOG;
import static hanglog.trip.fixture.DayLogFixture.EXPENSE_LONDON_DAYLOG;
import static hanglog.trip.fixture.ExpenseFixture.EURO_10000;
import static hanglog.trip.fixture.ExpenseFixture.JPY_10000;
import static hanglog.trip.fixture.ItemFixture.AIRPLANE_ITEM;
import static hanglog.trip.fixture.ItemFixture.JAPAN_HOTEL;
import static hanglog.trip.fixture.ItemFixture.LONDON_EYE_ITEM;
import static hanglog.trip.fixture.TripFixture.LONDON_TO_JAPAN;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import hanglog.category.dto.CategoryResponse;
import hanglog.expense.domain.CategoryExpense;
import hanglog.expense.domain.DayLogExpense;
import hanglog.expense.domain.repository.CurrencyRepository;
import hanglog.expense.dto.response.CategoryExpenseResponse;
import hanglog.expense.dto.response.DayLogExpenseResponse;
import hanglog.expense.dto.response.ItemDetailResponse;
import hanglog.expense.dto.response.TripExpenseResponse;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(MockitoExtension.class)
@Transactional
class ExpenseServiceTest {

    @InjectMocks
    private ExpenseService expenseService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private TripCityRepository tripCityRepository;


    @DisplayName("경비를 반환한다")
    @Test
    void getAllExpenses() {
        // given
        when(tripRepository.findById(1L))
                .thenReturn(Optional.of(LONDON_TO_JAPAN));
        when(currencyRepository.findByDate(any(LocalDate.class)))
                .thenReturn(Optional.of(DEFAULT_CURRENCY));
        when(tripCityRepository.findByTripId(1L))
                .thenReturn(List.of(new TripCity(LONDON_TRIP, LONDON), new TripCity(LONDON_TRIP, TOKYO)));
        final List<ItemDetailResponse> expectItemList = List.of(
                ItemDetailResponse.of(LONDON_EYE_ITEM),
                ItemDetailResponse.of(JAPAN_HOTEL),
                ItemDetailResponse.of(AIRPLANE_ITEM)
        );

        final int japanAmount = (int) (JPY_10000.getAmount() * DEFAULT_CURRENCY.getUnitRateOfJpy());
        final int londonAmount = (int) (EURO_10000.getAmount() * DEFAULT_CURRENCY.getEur());
        final int totalAmount = japanAmount + londonAmount * 2;

        // when
        final TripExpenseResponse actual = expenseService.getAllExpenses(1L);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual)
                    .usingRecursiveComparison()
                    .ignoringFields("categories", "categoryExpense", "dayLogs")
                    .isEqualTo(
                            TripExpenseResponse.of(
                                    LONDON_TO_JAPAN,
                                    totalAmount,
                                    List.of(new TripCity(LONDON_TRIP, LONDON), new TripCity(LONDON_TRIP, TOKYO)),
                                    List.of(
                                            new CategoryExpense(
                                                    EXPENSE_CATEGORIES.get(1),
                                                    londonAmount * 2,
                                                    totalAmount
                                            ),
                                            new CategoryExpense(
                                                    EXPENSE_CATEGORIES.get(1),
                                                    londonAmount * 2,
                                                    totalAmount
                                            )
                                    ),
                                    DEFAULT_CURRENCY,
                                    List.of(
                                            new DayLogExpense(EXPENSE_JAPAN_DAYLOG, japanAmount),
                                            new DayLogExpense(EXPENSE_LONDON_DAYLOG, londonAmount * 2)
                                    )
                            )
                    );
            softly.assertThat(actual.getCategories())
                    .usingRecursiveFieldByFieldElementComparator()
                    .contains(
                            new CategoryExpenseResponse(
                                    CategoryResponse.of(EXPENSE_CATEGORIES.get(3)),
                                    japanAmount,
                                    BigDecimal.valueOf((double) 100 * japanAmount / totalAmount)
                                            .setScale(2, RoundingMode.CEILING)),
                            new CategoryExpenseResponse(
                                    CategoryResponse.of(EXPENSE_CATEGORIES.get(1)),
                                    londonAmount * 2,
                                    BigDecimal.valueOf((double) 100 * londonAmount * 2 / totalAmount)
                                            .setScale(2, RoundingMode.CEILING))
                    );
            softly.assertThat(actual.getDayLogs())
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("items")
                    .contains(
                            new DayLogExpenseResponse(
                                    1L,
                                    1,
                                    LocalDate.of(2023, 7, 1),
                                    londonAmount * 2,
                                    List.of()
                            ),
                            new DayLogExpenseResponse(
                                    1L,
                                    2,
                                    LocalDate.of(2023, 7, 2),
                                    japanAmount,
                                    List.of()
                            )
                    );

            for (final DayLogExpenseResponse response : actual.getDayLogs()) {
                for (final ItemDetailResponse item : response.getItems()) {
                    softly.assertThat(expectItemList)
                            .usingRecursiveFieldByFieldElementComparator().
                            contains(item);
                }
            }
        });
    }

    @DisplayName("경비가 없는 여행일 경우 총 경비가 0으로 반환된다.")
    @Test
    void getNoExpenseTrip() {
        // given
        when(tripRepository.findById(1L))
                .thenReturn(Optional.of(LONDON_TRIP));
        when(currencyRepository.findByDate(any(LocalDate.class)))
                .thenReturn(Optional.of(DEFAULT_CURRENCY));
        when(tripCityRepository.findByTripId(1L))
                .thenReturn(List.of());

        // when
        final TripExpenseResponse actual = expenseService.getAllExpenses(1L);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(
                        TripExpenseResponse.of(
                                LONDON_TRIP,
                                0,
                                List.of(),
                                List.of(),
                                DEFAULT_CURRENCY,
                                List.of()
                        )
                );
    }
}

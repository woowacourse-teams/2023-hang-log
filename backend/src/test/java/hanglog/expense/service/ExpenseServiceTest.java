package hanglog.expense.service;


import static hanglog.category.fixture.CategoryFixture.ACCOMMODATION;
import static hanglog.category.fixture.CategoryFixture.EXPENSE_CATEGORIES;
import static hanglog.category.fixture.CategoryFixture.FOOD;
import static hanglog.category.fixture.CategoryFixture.SHOPPING;
import static hanglog.expense.fixture.CurrencyFixture.DEFAULT_CURRENCY;
import static hanglog.expense.fixture.ExchangeableExpenseFixture.EUR_100_SHOPPING;
import static hanglog.expense.fixture.ExchangeableExpenseFixture.KRW_100_FOOD;
import static hanglog.expense.fixture.ExchangeableExpenseFixture.USD_100_ACCOMMODATION;
import static hanglog.expense.fixture.TripExpenseFixture.DAYLOG_1_FOR_EXPENSE;
import static hanglog.expense.fixture.TripExpenseFixture.DAYLOG_2_FOR_EXPENSE;
import static hanglog.expense.fixture.TripExpenseFixture.TRIP_FOR_EXPENSE;
import static hanglog.trip.fixture.CityFixture.LONDON;
import static hanglog.trip.fixture.CityFixture.TOKYO;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;

import hanglog.category.domain.repository.CategoryRepository;
import hanglog.currency.domain.repository.CurrencyRepository;
import hanglog.expense.domain.Amount;
import hanglog.expense.domain.CategoryExpense;
import hanglog.expense.domain.DayLogExpense;
import hanglog.expense.dto.response.TripExpenseResponse;
import hanglog.expense.fixture.ExchangeableExpenseFixture.ExchangeableExpense;
import hanglog.trip.domain.TripCity;
import hanglog.trip.domain.repository.TripCityRepository;
import hanglog.trip.domain.repository.TripRepository;
import java.math.BigDecimal;
import java.util.Arrays;
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

    @Mock
    private CategoryRepository categoryRepository;

    @DisplayName("경비를 반환한다")
    @Test
    void getAllExpenses() {
        // given
        final List<TripCity> tripCities = List.of(
                new TripCity(TRIP_FOR_EXPENSE, LONDON),
                new TripCity(TRIP_FOR_EXPENSE, TOKYO)
        );
        when(tripRepository.findById(1L))
                .thenReturn(Optional.of(TRIP_FOR_EXPENSE));
        when(currencyRepository.findTopByOrderByDateAsc())
                .thenReturn(Optional.of(DEFAULT_CURRENCY));
        when(tripCityRepository.findByTripId(1L))
                .thenReturn(tripCities);
        when(categoryRepository.findExpenseCategory())
                .thenReturn(EXPENSE_CATEGORIES);

        final Amount day1Amount = getTotalAmount(Arrays.asList(KRW_100_FOOD, EUR_100_SHOPPING));
        final Amount day2Amount = getTotalAmount(List.of(USD_100_ACCOMMODATION));
        final Amount totalAmount = getTotalAmount(Arrays.asList(
                KRW_100_FOOD,
                EUR_100_SHOPPING,
                USD_100_ACCOMMODATION
        ));

        final TripExpenseResponse expected = TripExpenseResponse.of(
                TRIP_FOR_EXPENSE,
                totalAmount,
                tripCities,
                List.of(
                        new CategoryExpense(
                                SHOPPING,
                                EUR_100_SHOPPING.exchangeAmount,
                                totalAmount
                        ),
                        new CategoryExpense(
                                ACCOMMODATION,
                                USD_100_ACCOMMODATION.exchangeAmount,
                                totalAmount
                        ),
                        new CategoryExpense(
                                FOOD,
                                KRW_100_FOOD.exchangeAmount,
                                totalAmount
                        )
                ),
                DEFAULT_CURRENCY,
                List.of(
                        new DayLogExpense(DAYLOG_1_FOR_EXPENSE, day1Amount),
                        new DayLogExpense(DAYLOG_2_FOR_EXPENSE, day2Amount)
                )
        );
        final List<String> expectCategories = expected.getCategories().stream()
                .map(categoryExpenseResponse -> categoryExpenseResponse.getCategory().getName())
                .toList();

        // when
        final TripExpenseResponse actual = expenseService.getAllExpenses(1L);
        final List<String> actualCategories = actual.getCategories().stream()
                .filter(categoryExpenseResponse -> !categoryExpenseResponse.getAmount().equals(BigDecimal.ZERO))
                .map(categoryExpenseResponse -> categoryExpenseResponse.getCategory().getName())
                .toList();
        // then
        assertSoftly(softly -> {
            softly.assertThat(actual)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .ignoringFields("categories")
                    .isEqualTo(expected);
            softly.assertThat(actual.getCategories())
                    .usingRecursiveFieldByFieldElementComparatorOnFields()
                    .containsAll(expected.getCategories());
            softly.assertThat(actualCategories)
                    .isEqualTo(expectCategories);
        });
    }

    public Amount getTotalAmount(final List<ExchangeableExpense> expenses) {
        return expenses.stream()
                .map(exchangeableExpense -> exchangeableExpense.exchangeAmount)
                .reduce(Amount::add)
                .orElseGet(() -> new Amount(BigDecimal.ZERO));
    }

    @DisplayName("경비가 없는 여행일 경우 총 경비가 0으로 반환된다.")
    @Test
    void getNoExpenseTrip() {
        // given
        when(tripRepository.findById(1L))
                .thenReturn(Optional.of(LONDON_TRIP));
        when(currencyRepository.findTopByOrderByDateAsc())
                .thenReturn(Optional.of(DEFAULT_CURRENCY));
        when(tripCityRepository.findByTripId(1L))
                .thenReturn(List.of());
        when(categoryRepository.findExpenseCategory())
                .thenReturn(EXPENSE_CATEGORIES);

        // when
        final TripExpenseResponse actual = expenseService.getAllExpenses(1L);

        // then
        assertThat(actual).extracting("categories").asList().hasSize(6);
        assertThat(actual).extracting("dayLogs").asList().hasSize(3);
    }
}

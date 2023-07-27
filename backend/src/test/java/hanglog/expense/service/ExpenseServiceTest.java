package hanglog.expense.service;

import static hanglog.category.fixture.CategoryFixture.CULTURE;
import static hanglog.category.fixture.CategoryFixture.LODGING;
import static hanglog.expense.fixture.CurrenciesFixture.DEFAULT_CURRENCIES;
import static hanglog.trip.fixture.DayLogFixture.EXPENSE_JAPAN_DAYLOG;
import static hanglog.trip.fixture.DayLogFixture.EXPENSE_LONDON_DAYLOG;
import static hanglog.trip.fixture.ItemFixture.AIRPLANE_ITEM;
import static hanglog.trip.fixture.ItemFixture.JAPAN_HOTEL;
import static hanglog.trip.fixture.ItemFixture.LONDON_EYE_ITEM;
import static hanglog.trip.fixture.TripFixture.LONDON_TO_JAPAN;
import static hanglog.trip.fixture.TripFixture.LONDON_TRIP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import hanglog.expense.dto.response.CategoriesInExpenseResponse;
import hanglog.expense.dto.response.CategoryInExpenseResponse;
import hanglog.expense.dto.response.DayLogInExpenseResponse;
import hanglog.expense.dto.response.ExpenseGetResponse;
import hanglog.expense.dto.response.ItemInDayLogResponse;
import hanglog.expense.repository.CurrenciesRepository;
import hanglog.trip.domain.repository.TripRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    private CurrenciesRepository currenciesRepository;

    @DisplayName("경비를 반환한다")
    @Test
    void getAllExpenses() {
        // given
        when(tripRepository.findById(1L))
                .thenReturn(Optional.of(LONDON_TO_JAPAN));
        when(currenciesRepository.findCurrenciesBySearchDate(any(LocalDate.class)))
                .thenReturn(Optional.of(DEFAULT_CURRENCIES));
        final List<ItemInDayLogResponse> expectItemList = List.of(
                ItemInDayLogResponse.of(LONDON_EYE_ITEM),
                ItemInDayLogResponse.of(JAPAN_HOTEL),
                ItemInDayLogResponse.of(AIRPLANE_ITEM)
        );
        // when
        final ExpenseGetResponse actual = expenseService.getAllExpenses(1L);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual).usingRecursiveComparison()
                    .ignoringFields("categories", "dayLogs")
                    .isEqualTo(
                            ExpenseGetResponse.of(LONDON_TO_JAPAN,
                                    28009000,
                                    Map.of(LODGING, 9000, CULTURE, 28000000),
                                    DEFAULT_CURRENCIES,
                                    Map.of(EXPENSE_JAPAN_DAYLOG, 9000, EXPENSE_LONDON_DAYLOG, 28000000)
                            )
                    );
            softly.assertThat(actual.getCategories())
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("percentage")
                    .contains(
                            new CategoriesInExpenseResponse(
                                    CategoryInExpenseResponse.of(LODGING),
                                    9000,
                                    (double) 9000 / 28000000),
                            new CategoriesInExpenseResponse(
                                    CategoryInExpenseResponse.of(CULTURE),
                                    28000000,
                                    (double) 28000000 / 28009000)
                    );
            softly.assertThat(actual.getDayLogs()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("items")
                    .contains(
                            new DayLogInExpenseResponse(
                                    1L,
                                    1,
                                    LocalDate.of(2023, 7, 1),
                                    28000000,
                                    List.of()
                            ),
                            new DayLogInExpenseResponse(
                                    1L,
                                    2,
                                    LocalDate.of(2023, 7, 2),
                                    9000,
                                    List.of()
                            )
                    );

            for (final DayLogInExpenseResponse response : actual.getDayLogs()) {
                for (final ItemInDayLogResponse item : response.getItems()) {
                    softly.assertThat(expectItemList).usingRecursiveFieldByFieldElementComparator().
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
        when(currenciesRepository.findCurrenciesBySearchDate(any(LocalDate.class)))
                .thenReturn(Optional.of(DEFAULT_CURRENCIES));

        // when
        final ExpenseGetResponse actual = expenseService.getAllExpenses(1L);

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(
                        ExpenseGetResponse.of(LONDON_TRIP,
                                0,
                                Map.of(),
                                DEFAULT_CURRENCIES,
                                Map.of()
                        )
                );
    }


}

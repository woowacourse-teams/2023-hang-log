package hanglog.trip.domain;

import static hanglog.category.fixture.CategoryFixture.FOOD;
import static hanglog.expense.fixture.TripExpenseFixture.DAYLOG_1_FOR_EXPENSE;
import static hanglog.global.exception.ExceptionCode.INVALID_EXPENSE_OVER_MAX;
import static hanglog.global.exception.ExceptionCode.INVALID_EXPENSE_UNDER_MIN;
import static hanglog.global.exception.ExceptionCode.INVALID_RATING;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hanglog.expense.domain.Amount;
import hanglog.expense.domain.Expense;
import hanglog.global.exception.BadRequestException;
import hanglog.global.exception.InvalidDomainException;
import hanglog.trip.domain.type.ItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ItemTest {

    @DisplayName("별점이 N.5 형태가 아니라면 예외가 발생한다.")
    @Test
    void validateRatingFormat() {
        // given
        final Double invalidRating = 3.3;

        // when & then
        assertThatThrownBy(() -> createItemWithRating(invalidRating))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(INVALID_RATING.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_RATING.getCode());
    }

    @DisplayName("비용이 1억 초과이면 예외가 발생한다.")
    @Test
    void validateExpenseRange_OverMax() {
        // given
        final Amount amountOverMax = new Amount(100_000_001);

        // when & then
        assertThatThrownBy(() -> createItemWithAmount(amountOverMax))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage(INVALID_EXPENSE_OVER_MAX.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_EXPENSE_OVER_MAX.getCode());
    }

    @DisplayName("비용이 0원 미만이면 예외가 발생한다.")
    @Test
    void validateExpenseRange_UnderMin() {
        // given
        final Amount amountUnderMin = new Amount(-1);

        // when & then
        assertThatThrownBy(() -> createItemWithAmount(amountUnderMin))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage(INVALID_EXPENSE_UNDER_MIN.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_EXPENSE_UNDER_MIN.getCode());
    }

    private void createItemWithRating(final Double rating) {
        new Item(
                1L,
                ItemType.NON_SPOT,
                "title",
                1,
                rating,
                null,
                null,
                DAYLOG_1_FOR_EXPENSE,
                null
        );
    }

    private void createItemWithAmount(final Amount amount) {
        final Expense expense = new Expense(
                1L,
                "KRW",
                amount,
                FOOD
        );

        new Item(
                1L,
                ItemType.NON_SPOT,
                "title",
                1,
                null,
                null,
                null,
                DAYLOG_1_FOR_EXPENSE,
                expense
        );
    }
}

package hanglog.expense.domain;

import static hanglog.global.exception.ExceptionCode.INVALID_EXPENSE_OVER_MAX;
import static hanglog.global.exception.ExceptionCode.INVALID_EXPENSE_UNDER_MIN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hanglog.global.exception.InvalidDomainException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AmountTest {

    @DisplayName("비용이 1억 초과이면 예외가 발생한다.")
    @Test
    void validateAmount_OverMax() {
        // given
        final BigDecimal amountValue = BigDecimal.valueOf(100_000_001);

        // when & then
        assertThatThrownBy(() -> new Amount(amountValue))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage(INVALID_EXPENSE_OVER_MAX.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_EXPENSE_OVER_MAX.getCode());
    }

    @DisplayName("비용이 0원 미만이면 예외가 발생한다.")
    @Test
    void validateAmount_UnderMin() {
        // given
        final BigDecimal amountValue = BigDecimal.valueOf(-1);

        // when & then
        assertThatThrownBy(() -> new Amount(amountValue))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage(INVALID_EXPENSE_UNDER_MIN.getMessage())
                .extracting("code")
                .isEqualTo(INVALID_EXPENSE_UNDER_MIN.getCode());
    }
}

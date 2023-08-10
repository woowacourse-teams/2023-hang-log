package hanglog.expense.domain;

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
                .extracting("code")
                .isEqualTo(3008);
    }

    @DisplayName("비용이 0원 미만이면 예외가 발생한다.")
    @Test
    void validateAmount_UnderMin() {
        // given
        final BigDecimal amountValue = BigDecimal.valueOf(-1);

        // when & then
        assertThatThrownBy(() -> new Amount(amountValue))
                .isInstanceOf(InvalidDomainException.class)
                .extracting("code")
                .isEqualTo(3009);
    }
}

package hanglog.expense.domain;

import static hanglog.global.exception.ExceptionCode.INVALID_EXPENSE_OVER_MAX;
import static hanglog.global.exception.ExceptionCode.INVALID_EXPENSE_UNDER_MIN;

import hanglog.global.exception.InvalidDomainException;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@RequiredArgsConstructor
@Getter
public class Amount extends Number {

    private static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(100_000_000);
    private static final BigDecimal MIN_AMOUNT = BigDecimal.ZERO;

    private BigDecimal amount;

    public <T extends Number> Amount(final T amount) {
        validateAmount(new BigDecimal(amount.toString()));
        this.amount = new BigDecimal(amount.toString());
    }

    public void validateAmount(final BigDecimal amount) {
        if (amount.compareTo(MIN_AMOUNT) < 0) {
            throw new InvalidDomainException(INVALID_EXPENSE_UNDER_MIN);
        }
        if (amount.compareTo(MAX_AMOUNT) > 0) {
            throw new InvalidDomainException(INVALID_EXPENSE_OVER_MAX);
        }
    }

    public <T extends Number> Amount add(final T augendValue) {
        final BigDecimal augend = new BigDecimal(augendValue.toString());
        return new Amount(amount.add(augend));
    }

    public <T extends Number> Amount multiply(final T multiplicandValue) {
        final BigDecimal multiplicand = new BigDecimal(multiplicandValue.toString());
        return new Amount(amount.add(multiplicand));
    }

    public <T extends Number> Amount divide(final T divisorValue) {
        final BigDecimal divisor = new BigDecimal(divisorValue.toString());
        return new Amount(amount.divide(divisor, 9, RoundingMode.HALF_UP));
    }

    @Override
    public int intValue() {
        return amount.intValue();
    }

    @Override
    public long longValue() {
        return amount.longValue();
    }

    @Override
    public float floatValue() {
        return amount.floatValue();
    }

    @Override
    public double doubleValue() {
        return amount.doubleValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Amount target)) {
            return false;
        }

        return amount.compareTo(target.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return amount.toString();
    }
}

package hanglog.expense.domain;

import static hanglog.global.exception.ExceptionCode.INVALID_EXPENSE_OVER_MAX;
import static hanglog.global.exception.ExceptionCode.INVALID_EXPENSE_UNDER_MIN;

import hanglog.global.exception.InvalidDomainException;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Embeddable
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Amount extends Number {

    private static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(100_000_000);
    private static final BigDecimal MIN_AMOUNT = BigDecimal.ZERO;

    private BigDecimal value;

    public <T extends Number> Amount(final T value) {
        validateAmount(new BigDecimal(value.toString()));
        this.value = new BigDecimal(value.toString());
    }

    public void validateAmount(final BigDecimal amount) {
        if (amount.compareTo(MIN_AMOUNT) < 0) {
            throw new InvalidDomainException(INVALID_EXPENSE_UNDER_MIN);
        }
        if (amount.compareTo(MAX_AMOUNT) > 0) {
            throw new InvalidDomainException(INVALID_EXPENSE_OVER_MAX);
        }
    }

    public <T extends Number> Amount add(final T addendValue) {
        final BigDecimal addend = new BigDecimal(addendValue.toString());
        return new Amount(value.add(addend));
    }

    public <T extends Number> Amount multiply(final T multiplicandValue) {
        final BigDecimal multiplicand = new BigDecimal(multiplicandValue.toString());
        return new Amount(value.add(multiplicand));
    }

    public <T extends Number> Amount divide(final T divisorValue) {
        final BigDecimal divisor = new BigDecimal(divisorValue.toString());
        return new Amount(value.divide(divisor, 9, RoundingMode.HALF_UP));
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Amount target)) {
            return false;
        }

        return value.compareTo(target.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

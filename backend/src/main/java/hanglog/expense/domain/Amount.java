package hanglog.expense.domain;

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

    public static final Amount ZERO = new Amount(0);

    private BigDecimal value;

    public <T extends Number> Amount(final T value) {
        this.value = new BigDecimal(value.toString());
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

    public <T extends Number> int compareTo(final T targetValue) {
        return this.value.compareTo(new BigDecimal(targetValue.toString()));
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

package hanglog.trip.dto.request.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<Rating, Double> {

    @Override
    public boolean isValid(final Double value, final ConstraintValidatorContext context) {
        final double decimal = getDecimal(value);
        return decimal == 0.0 || decimal == 0.5;
    }

    private double getDecimal(final Double value) {
        return value - value.intValue();
    }
}

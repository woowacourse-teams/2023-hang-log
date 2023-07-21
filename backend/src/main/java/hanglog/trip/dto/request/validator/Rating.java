package hanglog.trip.dto.request.validator;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatingValidator.class)
public @interface Rating {

    String message() default "별점은 N.0점이거나 N.5점 형태이어야 합니다.";

    Class[] groups() default {};

    Class[] payload() default {};
}

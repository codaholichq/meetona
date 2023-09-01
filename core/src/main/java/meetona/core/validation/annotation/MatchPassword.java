package meetona.core.validation.annotation;

import meetona.core.validation.validator.MatchPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchPasswordValidator.class)
@Documented
public @interface MatchPassword {
    String message() default "The new passwords must match";

    Class<?>[] groups() default {};

    boolean allowNull() default false;

    Class<? extends Payload>[] payload() default {};
}
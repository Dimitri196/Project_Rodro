package cz.rodro.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPartialDateValidator.class)
@Documented
@Repeatable(ValidPartialDates.class) // make it repeatable
public @interface ValidPartialDate {

    String message() default "Invalid date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** Name of the year field in the target class */
    String yearField();

    /** Name of the month field in the target class */
    String monthField();

    /** Name of the day field in the target class */
    String dayField();

    /** Allow negative years (BC) */
    boolean allowNegativeYear() default false;
}

package cz.rodro.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class PastOrPresentYearValidator implements ConstraintValidator<PastOrPresentYear, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull should handle null if needed
        }
        int currentYear = Year.now().getValue();
        return value <= currentYear;
    }
}

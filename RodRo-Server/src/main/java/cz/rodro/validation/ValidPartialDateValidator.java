package cz.rodro.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.DateTimeException;
import java.time.LocalDate;

public class ValidPartialDateValidator implements ConstraintValidator<ValidPartialDate, Object> {

    private String yearField;
    private String monthField;
    private String dayField;
    private boolean allowNegativeYear;

    @Override
    public void initialize(ValidPartialDate constraintAnnotation) {
        this.yearField = constraintAnnotation.yearField();
        this.monthField = constraintAnnotation.monthField();
        this.dayField = constraintAnnotation.dayField();
        this.allowNegativeYear = constraintAnnotation.allowNegativeYear();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            PropertyDescriptor yearDesc = BeanUtils.getPropertyDescriptor(value.getClass(), yearField);
            PropertyDescriptor monthDesc = BeanUtils.getPropertyDescriptor(value.getClass(), monthField);
            PropertyDescriptor dayDesc = BeanUtils.getPropertyDescriptor(value.getClass(), dayField);

            if (yearDesc == null || monthDesc == null || dayDesc == null) return false;

            Method yearGetter = yearDesc.getReadMethod();
            Method monthGetter = monthDesc.getReadMethod();
            Method dayGetter = dayDesc.getReadMethod();

            if (yearGetter == null || monthGetter == null || dayGetter == null) return false;

            Integer year = (Integer) yearGetter.invoke(value);
            Integer month = (Integer) monthGetter.invoke(value);
            Integer day = (Integer) dayGetter.invoke(value);

            if (year == null || month == null || day == null) return true;

            if (!allowNegativeYear && year < 0) return false;

            return isValidDate(year, month, day);
        } catch (Exception e) {

            return false;
        }
    }

    private boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}

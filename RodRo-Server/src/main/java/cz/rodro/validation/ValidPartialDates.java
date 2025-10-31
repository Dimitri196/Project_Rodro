package cz.rodro.validation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPartialDates {
    ValidPartialDate[] value();
}
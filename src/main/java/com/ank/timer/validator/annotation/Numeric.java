package com.ank.timer.validator.annotation;

import com.ank.timer.validator.NumericValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER })
@Constraint(validatedBy = { NumericValidator.class })
public @interface Numeric {
    String message() default "Only Numeric value is allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

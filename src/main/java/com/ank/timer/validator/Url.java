package com.ank.timer.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@Constraint(validatedBy = { WebhookUrlValidator.class })
public @interface Url {
    String message() default "Entered value is not valid. Only Alphabetic value is allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

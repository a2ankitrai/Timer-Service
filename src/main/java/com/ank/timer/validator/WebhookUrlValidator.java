package com.ank.timer.validator;

import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WebhookUrlValidator implements ConstraintValidator<Url, String> {

    UrlValidator urlValidator = new UrlValidator();

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return urlValidator.isValid(s);

    }
}

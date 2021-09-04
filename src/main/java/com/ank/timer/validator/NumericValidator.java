package com.ank.timer.validator;

import com.ank.timer.validator.annotation.Numeric;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numeric, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return StringUtils.isNumeric(value);
    }
}

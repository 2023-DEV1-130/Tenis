package com.kata.tennis.validations;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GameRequestValidator implements ConstraintValidator<GameRequestConstraint, String> {

    @Override
    public void initialize(GameRequestConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isNotBlank(value) && value.toLowerCase().matches("(?i)a|b");
    }
}
package com.kata.tennis.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GameRequestValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GameRequestConstraint {
    String message() default "Invalid value for parameter. Allowed values are a & b";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
package de.comparus.testtask.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SqlParameterValidator implements ConstraintValidator<SqlProtected, String> {

    @Override
    public void initialize(SqlProtected constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String parameter, ConstraintValidatorContext context) {
        return parameter != null && parameter.matches("[a-zA-Z0-9-]*") && !parameter.contains("--");
    }
}

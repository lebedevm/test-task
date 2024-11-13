package de.comparus.testtask.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SqlParameterValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlProtected {

    String message() default "Wrong parameter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.phucth42.identity_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {DobValidator.class}
)
public @interface DobConstraint {
    String message() default "INVALID_DOB";

    int min();

    Class<?>[] groups() default {};
    Class<? extends Size> [] payload() default {};
}

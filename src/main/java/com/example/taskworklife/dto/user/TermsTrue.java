package com.example.taskworklife.dto.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TermsTrueValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TermsTrue {
    String message() default "Terms moeten geaccepteerd worden";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

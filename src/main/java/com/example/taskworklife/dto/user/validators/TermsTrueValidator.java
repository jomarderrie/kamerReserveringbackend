package com.example.taskworklife.dto.user.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TermsTrueValidator implements ConstraintValidator<TermsTrue, Boolean> {


    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        if (Boolean.FALSE.equals(value)) {
            return false;
        }else{
            return true;
        }
    }
}

package com.example.taskworklife.dto.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TermsTrueValidator implements ConstraintValidator<TermsTrue, Boolean> {


    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        if (!value) {
            return false;
        }else{
            return true;
        }
    }
}

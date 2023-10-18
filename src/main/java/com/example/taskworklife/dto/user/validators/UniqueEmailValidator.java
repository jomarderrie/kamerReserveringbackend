package com.example.taskworklife.dto.user.validators;

import com.example.taskworklife.models.user.User;
import com.example.taskworklife.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniekeMail, String> {
    @Autowired
    UserRepo userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User inDB = userRepository.findUserByEmail(value);
        if (inDB == null) {

            return true;
        } else {
            return false;
        }
    }
}

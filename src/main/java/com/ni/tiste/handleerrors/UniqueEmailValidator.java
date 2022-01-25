package com.ni.tiste.handleerrors;

import com.ni.tiste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService.findUserByEmail(email).isEmpty();
    }
}

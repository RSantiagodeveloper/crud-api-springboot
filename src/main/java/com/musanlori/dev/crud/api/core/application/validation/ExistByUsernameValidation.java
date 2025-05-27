package com.musanlori.dev.crud.api.core.application.validation;

import com.musanlori.dev.crud.api.core.domain.services.definitions.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    @Autowired
    private IUserService service;

    /**
     * initialize a custom validation.
     * @param constraintAnnotation param
     * */
    @Override
    public void initialize(final ExistsByUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * checks if the value string is a user that exist in DDBB.
     * @param value username.
     * @param context context.
     * @return boolean
     * */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return !service.existByUsername(value);
    }
}

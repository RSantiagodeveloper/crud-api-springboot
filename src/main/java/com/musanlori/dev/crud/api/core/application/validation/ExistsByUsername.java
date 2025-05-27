package com.musanlori.dev.crud.api.core.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistByUsernameValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByUsername {

    /**
     * message of the validator.
     * @return string.
     * */
    String message() default "username is already exist in database";

    /**
     * javadoc.
     * @return generic.
     * */
    Class<?>[] groups() default { };

    /**
     * javadoc.
     * @return generic.
     * */
    Class<? extends Payload>[] payload() default { };
}

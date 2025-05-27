package com.musanlori.dev.crud.api.core.application.validation;

import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ValidationErrorsController {

    /**
     * launch the validation error in the response using RequestDataNoValidException.
     * @param result object with errors detected.
     * */
    public static void throwValidationError(final BindingResult result) {

        List<Map<String, String>> errors = new ArrayList<>();

        result.getFieldErrors().forEach(fieldError -> {
            Map<String, String> err = new HashMap<>();
            err.put("field: ", fieldError.getField());
            err.put("message: ", fieldError.getDefaultMessage());
            errors.add(err);
        });

        throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                ErrorServiceMessages.REQUEST_FIELD_MSG,
                errors);
    }

}

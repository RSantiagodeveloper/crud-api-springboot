package com.musanlori.dev.crud.api.core.errors.exceptions;

import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import lombok.Getter;

@Getter
public class NotFoundResourceException extends RuntimeException {

    private final String code;

    /**
     * Lanza la exception utilizando solo un mensaje determinado
     * para que el servicio lo informe en la respuesta.
     * @param message mensaje de la exception.
     * */
    public NotFoundResourceException(final String message) {
        super(message);
        code = ErrorServiceMessages.GRAL_ERROR_CODE;
    }

    /**
     * Lanza la exception utilizando el codigo de error del servicio
     * y un mensaje para que el servicio lo informe en la respuesta.
     * @param code codigo de error.
     * @param message mensaje de la exception.
     * */
    public NotFoundResourceException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    /**
     * Lanza la exception utilizando el codigo de error del servicio
     * y un mensaje para que el servicio lo informe en la respuesta
     * y la especificacion del elemento que disparo la exception.
     * @param code codigo de error.
     * @param message mensaje de la exception.
     * @param field nombre del componente o elemento que causo la exception.
     * */
    public NotFoundResourceException(final String code, final String message, final String field) {
        super(String.format(message, field));
        this.code = code;
    }

}

package com.musanlori.dev.crud.api.core.errors.exceptions;

import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import lombok.Getter;

@Getter
public class LogicServiceConflictException extends RuntimeException {

    private final String code;

    /**
     * Lanza la exception utilizando un mensaje para que el servicio lo informe en la respuesta.
     * @param msg mensaje de la exception.
     * */
    public LogicServiceConflictException(final String msg) {
        super(msg);
        code = ErrorServiceMessages.GRAL_ERROR_CODE;
    }

    /**
     * Lanza la exception utilizando el codigo de error del servicio
     * y un mensaje para que el servicio lo informe en la respuesta.
     * @param code codigo de error.
     * @param msg mensaje de la exception.
     * */
    public LogicServiceConflictException(final String code, final String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * Lanza la exception utilizando el codigo de error del servicio
     * y un mensaje para que el servicio lo informe en la respuesta
     * y la especificacion del elemento que disparo la exception.
     * @param code codigo de error.
     * @param msg mensaje de la exception.
     * @param field nombre del componente o elemento que causo la exception.
     * */
    public LogicServiceConflictException(final String code, final String msg, final String field) {
        super(String.format(msg, field));
        this.code = code;
    }
}

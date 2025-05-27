package com.musanlori.dev.crud.api.core.errors.exceptions;

import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class RequestDataNotValidException extends RuntimeException {

    private final String code;

    private final List<Map<String, String>> errors;

    /**
     * Lanza la exception utilizando el  un mensaje para que el servicio lo informe en la respuesta.
     * @param msg mensaje de la exception.
     * */
    public RequestDataNotValidException(final String msg) {
        super(msg);
        code = ErrorServiceMessages.GRAL_ERROR_CODE;
        this.errors = null;
    }

    /**
     * Lanza la exception utilizando el  un mensaje para que el servicio lo informe en la respuesta.
     * @param code codigo de error.
     * @param msg mensaje de la exception.
     * */
    public RequestDataNotValidException(final String code, final String msg) {
        super(msg);
        this.code = code;
        this.errors = null;
    }

    /**
     * Lanza la exception utilizando el codigo de error del servicio
     * y un mensaje para que el servicio lo informe en la respuesta.
     * @param code codigo de error.
     * @param msg mensaje de la exception.
     * @param errors listado de errores detectados.
     * */
    public RequestDataNotValidException(final String code, final String msg, final List<Map<String, String>> errors) {
        super(msg);
        this.code = code;
        this.errors = errors;
    }

    /**
     * Lanza la exception utilizando el codigo de error del servicio
     * y un mensaje para que el servicio lo informe en la respuesta
     * y la especificacion del elemento que disparo la exception.
     * @param code codigo de error.
     * @param msg mensaje de la exception.
     * @param field nombre del componente o elemento que causo la exception.
     * */
    public RequestDataNotValidException(final String code, final String msg, final String field) {
        super(String.format(msg, field));
        this.code = code;
        this.errors = null;
    }

}

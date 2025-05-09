package com.musanlori.dev.crud.api.core.application.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GeneralResponseService {

    @Schema(name = "codigo", description = "response code", type = "string")
    private String codigo;

    @Schema(name = "mensaje", description = "service message", type = "string")
    private String mensaje;

    @Schema(name = "timestamp", description = "response datetime", type = "string")
    private Date timestamp;

    /**
     * Constructor para crear respuesta sin inicializar.
     * */
    public GeneralResponseService() { }

    /**
     * Constructor para crear respuesta rapida.
     * @param codigo codigo de error reportado.
     * @param mensaje mensjae de error reportado.
     * */
    public GeneralResponseService(final String codigo, final String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.timestamp = new Date();
    }
}

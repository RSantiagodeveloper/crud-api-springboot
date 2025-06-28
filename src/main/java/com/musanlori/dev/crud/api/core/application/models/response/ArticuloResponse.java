package com.musanlori.dev.crud.api.core.application.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticuloResponse {

    @Schema(name = "uuid", description = "article uuid", type = "string", example = "uuid-1234-5678-9012")
    private String uuid;

    @Schema(name = "nombre", description = "article name", type = "string", example = "ARTICLE_NAME")
    private String nombre;

    @Schema(name = "descripcion", description = "article description", type = "string", example = "Lorem Ipsum is ...")
    private String descripcion;

    @Schema(name = "precio", description = "article price", type = "double", example = "99.99")
    private Double precio;

    @Schema(name = "unidades", description = "article units", type = "int", example = "100")
    private Integer unidades;

    @Schema(name = "calificacion", description = "article rating", type = "double", example = "4.9")
    private Double calificacion;

    @Schema(name = "tipo", description = "article type", type = "string", example = "TIPO_DESCRIPTION")
    private String tipo;

    @Schema(name = "franquicia", description = "article franchise", type = "string", example = "FRANQUICIA_DESCRIPTION")
    private String franquicia;

    @Schema(name = "fabricante", description = "article manufacturer", type = "string", example = "FABRICANTE_DESCRIPTION")
    private String fabricante;

    @Schema(name = "editorial", description = "article editorial", type = "string", example = "EDITORIAL_DESCRIPTION")
    private String editorial;

    @Schema(name = "created", description = "creating timestamp", type = "string", example = "dd/MM/yyyy HH:mm:ss")
    private String created;

    @Schema(name = "updated", description = "updating timestamp", type = "string", example = "dd/MM/yyyy HH:mm:ss")
    private String updated;
}

package com.musanlori.dev.crud.api.core.application.models.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticuloRequestModel {

    @Schema(name = "uuid", description = "article uuid", type = "string", example = "uuid-1234-5678-9012",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 36)
    private String uuid;

    @Schema(name = "nombre", description = "article name", type = "string", example = "Article name",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    @Size(max = 92)
    private String nombre;

    @Schema(name = "description", description = "article description", type = "string", example = "Lorem is a ...",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    @Size(max = 92)
    private String descripcion;

    @Schema(name = "precio", description = "article description", type = "double", example = "99.99",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    @Size(max = 92)
    private Double precio;

    @Schema(name = "unidades", description = "article units", type = "int", example = "100",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer unidades;

    @Schema(name = "calificacion", description = "article rating", type = "double", example = "5.0",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Double calificacion;

    private CatalogosRequestModel tipo;

    private CatalogosRequestModel franquicia;

    private CatalogosRequestModel fabricante;

    private CatalogosRequestModel editorial;
}

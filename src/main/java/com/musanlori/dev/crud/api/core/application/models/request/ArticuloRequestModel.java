package com.musanlori.dev.crud.api.core.application.models.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticuloRequestModel {

    @Size(max = 36)
    private String uuid;

    @NotNull
    @NotBlank
    @Size(max = 92)
    private String nombre;

    @NotNull
    @NotBlank
    @Size(max = 92)
    private String descripcion;

    @NotNull
    @NotBlank
    @Size(max = 92)
    private Double precio;
    private Integer unidades;
    private Double calificacion;
    private CatalogosRequestModel tipo;
    private CatalogosRequestModel franquicia;
    private CatalogosRequestModel fabricante;
    private CatalogosRequestModel editorial;
}

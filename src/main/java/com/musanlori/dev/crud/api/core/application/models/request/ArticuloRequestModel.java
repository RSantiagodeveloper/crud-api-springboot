package com.musanlori.dev.crud.api.core.application.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticuloRequestModel {
    private String uuid;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer unidades;
    private Double calificacion;
    private CatalogosRequestModel tipo;
    private CatalogosRequestModel franquicia;
    private CatalogosRequestModel fabricante;
    private CatalogosRequestModel editorial;
}

package com.musanlori.dev.crud.api.core.application.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticuloResponse {
    private String uuid;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer unidades;
    private Double calificacion;
    private String tipo;
    private String franquicia;
    private String fabricante;
    private String editorial;
    private String created;
    private String update;
}

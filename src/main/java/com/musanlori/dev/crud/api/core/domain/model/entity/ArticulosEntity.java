package com.musanlori.dev.crud.api.core.domain.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "articulos")
public class ArticulosEntity {

    @Id
    @Column(name = "id_articulo", length = 36, nullable = false)
    private String idArticulo;

    @Column(name = "nombre", length = 96, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 512, nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "unidades")
    private Integer unidades;

    @Column(name = "calificacion")
    private Double calificacion;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    private CatTiposEntity tipo;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_franquicia", referencedColumnName = "id_franquicia")
    private CatFranquiciasEntity franquicia;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fabricante", referencedColumnName = "id_fabricante")
    private CatFabricantesEntity fabricante;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_editorial", referencedColumnName = "id_editorial")
    private CatEditorialesEntity editorial;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private Date updatedAt;
}

package com.musanlori.dev.crud.api.core.application.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogosRequestModel {
    private String uuidElement;
    private Integer idElement;
    private String description;
}

package com.musanlori.dev.crud.api.core.application.models.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogosRequestModel {

    @Size(max = 36)
    private String uuidElement;

    private Integer idElement;

    @NotNull
    @NotEmpty
    private String description;
}

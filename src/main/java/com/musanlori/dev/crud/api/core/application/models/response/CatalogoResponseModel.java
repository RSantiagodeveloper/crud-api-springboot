package com.musanlori.dev.crud.api.core.application.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoResponseModel {

    @Schema(name = "id", description = "id element value", type = "string", example = "ID_ELEMENT_ON_DB")
    private String id;

    @Schema(name = "description", description = "element value", type = "string", example = "ELEMENT_DESCRIPTION")
    private String description;

    @Schema(name = "created", description = "creating timestamp", example = "dd/MM/yyyy HH:mm:ss")
    private String created;

    @Schema(name = "updated", description = "updating timestamp", example = "dd/MM/yyyy HH:mm:ss")
    private String updated;
}

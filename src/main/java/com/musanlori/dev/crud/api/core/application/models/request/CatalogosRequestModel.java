package com.musanlori.dev.crud.api.core.application.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogosRequestModel {

    @Schema(name = "uuidElement", description = "uuid-string only in the catalogues that uses uuid", type = "string",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "UUID-1234-5678-9012")
    @Size(max = 36)
    private String uuidElement;

    @Schema(name = "idElement", description = "id only in the catalogues that uses integer id", type = "int",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Integer idElement;

    @Schema(name = "description", description = "values that requires save in the catalogue", type = "string",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "value")
    @NotNull
    @NotEmpty
    private String description;
}

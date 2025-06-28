package com.musanlori.dev.crud.api.core.application.models.request;

import com.musanlori.dev.crud.api.core.application.validation.ExistsByUsername;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @Schema(name = "username", description = "username data", type = "String", example = "myUsername2")
    // todo: corregir validacion personalizada de campo
    @ExistsByUsername
    private String username;

    @Schema(name = "password", description = "password string", type = "string", example = "$ecur3P4$$5trinG")
    @NotBlank
    private String password;

    @Schema(name = "admin", description = "admin flag", type = "boolean", example = "true/false")
    private boolean admin;
}

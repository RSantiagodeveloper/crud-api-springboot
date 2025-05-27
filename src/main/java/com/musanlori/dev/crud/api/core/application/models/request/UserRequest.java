package com.musanlori.dev.crud.api.core.application.models.request;

import com.musanlori.dev.crud.api.core.application.validation.ExistsByUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    // todo: corregir validacion personalizada de campo
    @ExistsByUsername
    private String username;

    @NotBlank
    private String password;

    private boolean admin;
}

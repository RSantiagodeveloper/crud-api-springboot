package com.musanlori.dev.crud.api.core.application.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponse {

    @Schema(name = "username", description = "username data", type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "my_user_name")
    private String username;

    @Schema(name = "role", description = "name of role", type = "String[]",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "ROLE_GUESS, ROLE_ADDITIONAL")
    private List<String> role;

    @Schema(name = "enable", description = "account status", type = "boolean",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private boolean enable;

    /**
     * constructor by default: Init default values in props.
     * */
    public UserResponse() {
        this.username = null;
        this.role = new ArrayList<>();
        this.enable = false;
    }

}

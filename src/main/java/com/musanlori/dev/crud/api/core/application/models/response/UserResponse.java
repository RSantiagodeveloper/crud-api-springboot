package com.musanlori.dev.crud.api.core.application.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponse {
    private String username;
    private List<String> role;
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

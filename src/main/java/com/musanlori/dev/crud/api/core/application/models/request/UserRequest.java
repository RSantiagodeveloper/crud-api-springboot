package com.musanlori.dev.crud.api.core.application.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String password;
    private boolean admin;
}

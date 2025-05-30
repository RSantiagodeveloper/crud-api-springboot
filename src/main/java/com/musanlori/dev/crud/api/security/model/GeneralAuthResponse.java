package com.musanlori.dev.crud.api.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralAuthResponse {
    private String username;
    private String message;
    private String token;
    private String error;
    private Date timestamp;

    public GeneralAuthResponse() {
        this.timestamp = new Date();
    }

    public GeneralAuthResponse(final String username, final String message, final String token) {
        this.username = username;
        this.message = message;
        this.token = token;
        this.error = null;
        this.timestamp = new Date();
    }

    public GeneralAuthResponse(final String message, final String error) {
        this.username = null;
        this.message = message;
        this.token = null;
        this.error = error;
        this.timestamp = new Date();
    }

}

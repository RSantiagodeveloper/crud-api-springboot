package com.musanlori.dev.crud.api.security.util;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public final class Constants {

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String AUTHORITIES_KEY = "authorities";
    public static final String USERNAME_KEY = "username";
    public static final String MSG_KEY = "msg";
    public static final String TOKEN_KEY = "token";
    public static final String ERROR_KEY = "error";

    public static final String SUCCESS_LOGIN_MSG = "Success login";
    public static final String ERROR_LOGIN_MSG = "Invalid credentials";
    public static final String ERROR_JWT_MSG = "Invalid JWT token";

    private Constants() { }

}

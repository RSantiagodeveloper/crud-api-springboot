package com.musanlori.dev.crud.api.security.util;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public final class Constants {

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String AUTHORITIES_KEY = "authorities";

    public static final String SUCCESS_LOGIN_MSG = "Success login";
    public static final String ERROR_LOGIN_MSG = "Invalid credentials";
    public static final String ERROR_JWT_MSG = "Invalid JWT token";

    public static final int TIME_TO_EXPIRE = 3600000;

    private Constants() { }

}

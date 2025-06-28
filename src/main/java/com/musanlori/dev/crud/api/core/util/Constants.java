package com.musanlori.dev.crud.api.core.util;

public class Constants {
    public static final String CRUD_BASE_PATH = "crud-api/";
    public static final String AUTH_BASE_PATH = "auth/";

    // witheList swagger paths
    public static final String[] API_DOCS_OPATH = {
            "/services/api-docs/**",
            "/services/swagger-ui/**",
            "/services/swagger-ui.html"
    };

    //roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public static final String ADMIN_STR = "ADMIN";
    public static final String USER_STR = "USER";
}

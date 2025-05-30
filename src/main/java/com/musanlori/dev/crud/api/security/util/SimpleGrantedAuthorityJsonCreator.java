package com.musanlori.dev.crud.api.security.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {

    /**
     * Utilidad para transformar campos ROLE del SimpleGrantedAuthority de spring security,
     * en campos "authority" configurados en el filtro de authentication.
     * @param role role.
     * */
    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role) { }

}

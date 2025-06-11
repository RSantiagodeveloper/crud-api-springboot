package com.musanlori.dev.crud.api.security.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {

    /**
     * utility to transform field ROLE of SimpleGrantedAuthority from Spring Security package
     * to field "authority" configs on the Authentication Filter.
     * @param role role.
     * */
    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") final String role) { }

}

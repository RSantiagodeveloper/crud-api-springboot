package com.musanlori.dev.crud.api.core.application.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoResponseModel {
    private String id;
    private String description;
    private String created;
    private String updated;
}

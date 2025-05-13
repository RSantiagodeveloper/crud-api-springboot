package com.musanlori.dev.crud.api.core.domain.services.definitions;

import com.musanlori.dev.crud.api.core.application.models.request.ArticuloRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.ArticuloResponse;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;

import java.util.List;

public interface IArticuloService {

    /**
     * creates a new Articulo Register in DataBase.
     * @param request request object to create Articulo.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService addNewArticulo(ArticuloRequestModel request);

    /**
     * Query all Articulo Objects stored in DataBase.
     *
     * @return list of Articuloes.
     */
    List<ArticuloResponse> getAllArticulos();

    /**
     * Query an Articulo Object using its uuid.
     *
     * @param uuid Articulo's uuid.
     * @return {@link ArticuloResponse} service response.
     */
    ArticuloResponse getArticulo(String uuid);

    /**
     * updates an Articulo Register in DataBase.
     *
     * @param request request object to update Articulo.
     * @return {@link ArticuloResponse} service response.
     */
    ArticuloResponse updateArticulo(ArticuloRequestModel request);

    /**
     * Deletes an Articulo Register in DataBase.
     * @param uuid Articulo's id.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService deleteArticulo(String uuid);

}

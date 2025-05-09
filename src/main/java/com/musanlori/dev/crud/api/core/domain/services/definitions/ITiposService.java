package com.musanlori.dev.crud.api.core.domain.services.definitions;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;

import java.util.List;

public interface ITiposService {
    /**
     * creates a new Tipo Register in DataBase.
     * @param request request object to create Tipo.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService addNewTipo(CatalogosRequestModel request);

    /**
     * Query all Tipo Objects stored in DataBase.
     * @return list of Tipos.
     * */
    List<CatalogoResponseModel> getAllTipos();

    /**
     * Query a Tipo Object using its id.
     * @param id tipo's id.
     * @return {@link CatalogosRequestModel} service response.
     * */
    CatalogoResponseModel getTipo(Integer id);

    /**
     * updates a Tipo Register in DataBase.
     *
     * @param request request object to update Tipo.
     * @return {@link GeneralResponseService} service response.
     */
    CatalogoResponseModel updateTipo(CatalogosRequestModel request);

    /**
     * Deletes a Tipo Register in DataBase.
     * @param id Tipo's id.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService deleteTipo(Integer id);
}

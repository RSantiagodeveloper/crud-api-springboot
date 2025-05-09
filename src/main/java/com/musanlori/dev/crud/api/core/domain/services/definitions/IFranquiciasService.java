package com.musanlori.dev.crud.api.core.domain.services.definitions;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;

import java.util.List;

public interface IFranquiciasService {

    /**
     * creates a new Franquicia Register in DataBase.
     * @param request request object to create Franquicia.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService addNewFranquicia(CatalogosRequestModel request);

    /**
     * Query all Franquicia Objects stored in DataBase.
     * @return list of Franquicias.
     * */
    List<CatalogoResponseModel> getAllFranquicias();

    /**
     * Query a Franquicia Object using its uuid.
     * @param uuid franquicia's uuid.
     * @return {@link CatalogosRequestModel} service response.
     * */
    CatalogoResponseModel getFranquicia(String uuid);

    /**
     * updates a Franquicia Register in DataBase.
     *
     * @param request request object to update Franquicia.
     * @return {@link CatalogoResponseModel} service response.
     */
    CatalogoResponseModel updateFranquicia(CatalogosRequestModel request);

    /**
     * Deletes a Franquicia Register in DataBase.
     * @param uuid Franquicia's id.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService deleteFranquicia(String uuid);
}

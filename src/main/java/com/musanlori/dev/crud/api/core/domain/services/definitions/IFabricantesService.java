package com.musanlori.dev.crud.api.core.domain.services.definitions;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;

import java.util.List;

public interface IFabricantesService {

    /**
     * creates a new Fabricante Register in DataBase.
     * @param request request object to create Fabricante.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService addNewFabricante(CatalogosRequestModel request);

    /**
     * Query all Fabricante Objects stored in DataBase.
     * @return list of Fabricantes.
     * */
    List<CatalogoResponseModel> getAllFabricantes();

    /**
     * Query a Fabricante Object using its id.
     * @param uuid Franquicia's id.
     * @return {@link CatalogosRequestModel} service response.
     * */
    CatalogoResponseModel getFabricante(String uuid);

    /**
     * updates a Fabricante Register in DataBase.
     *
     * @param request request object to update Fabricante.
     * @return {@link GeneralResponseService} service response.
     */
    CatalogoResponseModel updateFabricante(CatalogosRequestModel request);

    /**
     * Deletes a Fabricante Register in DataBase.
     * @param uuid Fabricante's id.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService deleteFabricante(String uuid);
}

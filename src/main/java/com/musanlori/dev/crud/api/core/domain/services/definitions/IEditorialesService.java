package com.musanlori.dev.crud.api.core.domain.services.definitions;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;

import java.util.List;

public interface IEditorialesService {

    /**
     * creates a new Editorial Register in DataBase.
     * @param request request object to create Editorial.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService addNewEditorial(CatalogosRequestModel request);

    /**
     * Query all Editorial Objects stored in DataBase.
     * @return list of Editoriales.
     * */
    List<CatalogoResponseModel> getAllEditoriales();

    /**
     * Query an Editorial Object using its uuid.
     * @param uuid editorial's uuid.
     * @return {@link CatalogosRequestModel} service response.
     * */
    CatalogoResponseModel getEditorial(String uuid);

    /**
     * updates an Editorial Register in DataBase.
     * @param request request object to update Editorial.
     * @return {@link GeneralResponseService} service response.
     * */
    CatalogoResponseModel updateEditorial(CatalogosRequestModel request);

    /**
     * Deletes an Editorial Register in DataBase.
     * @param id Editorial's id.
     * @return {@link GeneralResponseService} service response.
     * */
    GeneralResponseService deleteEditorial(String id);
}

package com.musanlori.dev.crud.api.core.application.controllers;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IEditorialesService;
import com.musanlori.dev.crud.api.core.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = Constants.BASE_PATH)
public class EditorialesController {

    @Autowired
    private IEditorialesService service;

    /**
     * api to create a new Editorial Entity in DDBB.
     * @param request api request object.
     * @return {@link GeneralResponseService} api response object.
     * */
    @PostMapping("/editorial")
    public GeneralResponseService createEditorial(@RequestBody final CatalogosRequestModel request) {
        return service.addNewEditorial(request);
    }

    /**
     * api to read all Editorial Entities in DDBB.
     * @return {@link GeneralResponseService} api response object.
     * */
    @GetMapping("/editoriales")
    public List<CatalogoResponseModel> readEditorial() {
        return service.getAllEditoriales();
    }

    /**
     * api to read an Editorial Entitie in DDBB.
     * @param uuid uuid of the editorial.
     * @return {@link GeneralResponseService} api response object.
     * */
    @GetMapping("/editorial")
    public CatalogoResponseModel readEditorial(@RequestParam(name = "uuid") final String uuid) {
        return service.getEditorial(uuid);
    }

    /**
     * api to update an Editorial Entitie in DDBB.
     * @param request request object to update.
     * @return {@link GeneralResponseService} api response object.
     * */
    @PutMapping("/editorial")
    public CatalogoResponseModel updateEditorial(@RequestBody final CatalogosRequestModel request) {
        return service.updateEditorial(request);
    }

    /**
     * api to read an Editorial Entitie in DDBB.
     * @param uuid uuid of the editorial.
     * @return {@link GeneralResponseService} api response object.
     * */
    @DeleteMapping("/editorial")
    public GeneralResponseService deleteEditorial(@RequestParam(name = "uuid") final String uuid) {
        return service.deleteEditorial(uuid);
    }

}

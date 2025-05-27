package com.musanlori.dev.crud.api.core.application.controllers;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IFranquiciasService;
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
@RequestMapping(value = Constants.CRUD_BASE_PATH)
public class FranquiciasController {

    @Autowired
    private IFranquiciasService service;

    /**
     * api to create a new franquicia Entity in DDBB.
     * @param request api request object.
     * @return {@link GeneralResponseService} api response object.
     * */
    @PostMapping("/franquicia")
    public GeneralResponseService createFranquicia(@RequestBody final CatalogosRequestModel request) {
        return service.addNewFranquicia(request);
    }

    /**
     * api to read all franquicia Entities in DDBB.
     * @return {@link GeneralResponseService} api response object.
     * */
    @GetMapping("/franquicias")
    public List<CatalogoResponseModel> readFranquicia() {
        return service.getAllFranquicias();
    }

    /**
     * api to read an franquicia Entitie in DDBB.
     * @param uuid uuid of the franquicia.
     * @return {@link GeneralResponseService} api response object.
     * */
    @GetMapping("/franquicia")
    public CatalogoResponseModel readFranquicia(@RequestParam(name = "uuid") final String uuid) {
        return service.getFranquicia(uuid);
    }

    /**
     * api to update an franquicia Entitie in DDBB.
     * @param request request object to update.
     * @return {@link GeneralResponseService} api response object.
     * */
    @PutMapping("/franquicia")
    public CatalogoResponseModel updateFranquicia(@RequestBody final CatalogosRequestModel request) {
        return service.updateFranquicia(request);
    }

    /**
     * api to read an franquicia Entitie in DDBB.
     * @param uuid uuid of the franquicia.
     * @return {@link GeneralResponseService} api response object.
     * */
    @DeleteMapping("/franquicia")
    public GeneralResponseService deleteFranquicia(@RequestParam(name = "uuid") final String uuid) {
        return service.deleteFranquicia(uuid);
    }

}

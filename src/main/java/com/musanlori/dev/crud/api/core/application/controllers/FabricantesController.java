package com.musanlori.dev.crud.api.core.application.controllers;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IFabricantesService;
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
public class FabricantesController {

    @Autowired
    private IFabricantesService service;

    /**
     * api to create a new Editorial Entity in DDBB.
     * @param request api request object.
     * @return {@link GeneralResponseService} api response object.
     * */
    @PostMapping("/fabricante")
    public GeneralResponseService createFabricante(@RequestBody final CatalogosRequestModel request) {
        return service.addNewFabricante(request);
    }

    /**
     * api to read all Editorial Entities in DDBB.
     * @return {@link GeneralResponseService} api response object.
     * */
    @GetMapping("/fabricantes")
    public List<CatalogoResponseModel> readFabricantes() {
        return service.getAllFabricantes();
    }

    /**
     * api to read an Editorial Entitie in DDBB.
     * @param uuid uuid of the fabricante.
     * @return {@link GeneralResponseService} api response object.
     * */
    @GetMapping("/fabricante")
    public CatalogoResponseModel readFabricante(@RequestParam(name = "uuid") final String uuid) {
        return service.getFabricante(uuid);
    }

    /**
     * api to update an Editorial Entitie in DDBB.
     * @param request request object to update.
     * @return {@link GeneralResponseService} api response object.
     * */
    @PutMapping("/fabricante")
    public CatalogoResponseModel updateFabricante(@RequestBody final CatalogosRequestModel request) {
        return service.updateFabricante(request);
    }

    /**
     * api to read an Editorial Entitie in DDBB.
     * @param uuid uuid of the fabricante.
     * @return {@link GeneralResponseService} api response object.
     * */
    @DeleteMapping("/fabricante")
    public GeneralResponseService deleteFabricante(@RequestParam(name = "uuid") final String uuid) {
        return service.deleteFabricante(uuid);
    }

}

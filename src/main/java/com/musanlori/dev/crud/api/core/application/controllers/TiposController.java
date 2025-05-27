package com.musanlori.dev.crud.api.core.application.controllers;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.services.definitions.ITiposService;
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
public class TiposController {

    @Autowired
    private ITiposService service;

    /**
     * api to create a new tipo Entity in DDBB.
     * @param request api request object.
     * @return {@link GeneralResponseService} api response object.
     * */
    @PostMapping("/tipo")
    public GeneralResponseService createTipo(@RequestBody final CatalogosRequestModel request) {
        return service.addNewTipo(request);
    }

    /**
     * api to read all tipo Entities in DDBB.
     * @return {@link GeneralResponseService} api response object.
     * */
    @GetMapping("/tipos")
    public List<CatalogoResponseModel> readTipo() {
        return service.getAllTipos();
    }

    /**
     * api to read an tipo Entitie in DDBB.
     * @param uuid uuid of the tipo.
     * @return {@link GeneralResponseService} api response object.
     * */
    @GetMapping("/tipo")
    public CatalogoResponseModel readTipo(@RequestParam(name = "uuid") final Integer uuid) {
        return service.getTipo(uuid);
    }

    /**
     * api to update an tipo Entitie in DDBB.
     * @param request request object to update.
     * @return {@link GeneralResponseService} api response object.
     * */
    @PutMapping("/tipo")
    public CatalogoResponseModel updateTipo(@RequestBody final CatalogosRequestModel request) {
        return service.updateTipo(request);
    }

    /**
     * api to read an tipo Entitie in DDBB.
     * @param uuid uuid of the tipo.
     * @return {@link GeneralResponseService} api response object.
     * */
    @DeleteMapping("/tipo")
    public GeneralResponseService deleteTipo(@RequestParam(name = "uuid") final Integer uuid) {
        return service.deleteTipo(uuid);
    }

}

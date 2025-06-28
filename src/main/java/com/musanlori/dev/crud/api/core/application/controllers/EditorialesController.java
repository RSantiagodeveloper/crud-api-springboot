package com.musanlori.dev.crud.api.core.application.controllers;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IEditorialesService;
import com.musanlori.dev.crud.api.core.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
public class EditorialesController {

    @Autowired
    private IEditorialesService service;

    /**
     * api to create a new Editorial Entity in DDBB.
     * @param request api request object.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to create a new Editorial Entity in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "409", description = "Errors in the Logic Service Flow", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "500", description = "general error service response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            })
    })
    @PostMapping("/editorial")
    public GeneralResponseService createEditorial(@RequestBody @Valid final CatalogosRequestModel request) {
        return service.addNewEditorial(request);
    }

    /**
     * api to read all Editorial Entities in DDBB.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to read all Editorial Entities in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CatalogoResponseModel.class)))
            }),
            @ApiResponse(responseCode = "500", description = "general error service response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            })
    })
    @GetMapping("/editoriales")
    public List<CatalogoResponseModel> readEditorial() {
        return service.getAllEditoriales();
    }

    /**
     * api to read an Editorial Entitie in DDBB.
     * @param uuid uuid of the editorial.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to read an Editorial Entity in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoResponseModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "errors in the parameters of the request", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "404", description = "Does not exist the Entity in DB", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "500", description = "general error service response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            })
    })
    @GetMapping("/editorial")
    public CatalogoResponseModel readEditorial(
            @Parameter(name = "uuid", required = true, description = "query param for editorial Entity uuid")
            @RequestParam(name = "uuid") final String uuid) {
        return service.getEditorial(uuid);
    }

    /**
     * api to update an Editorial Entity in DDBB.
     * @param request request object to update.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to update an Editorial Entity in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogoResponseModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "errors in the parameters of the request", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "404", description = "Does not exist the Entity in DB", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "409", description = "Errors in the Logic Service Flow", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "500", description = "general error service response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            })
    })
    @PutMapping("/editorial")
    public CatalogoResponseModel updateEditorial(@RequestBody @Valid final CatalogosRequestModel request) {
        return service.updateEditorial(request);
    }

    /**
     * api to delete an Editorial Entitie in DDBB.
     * @param uuid uuid of the editorial.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to read an Editorial Entity in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "400", description = "errors in the parameters of the request", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "404", description = "Does not exist the Entity in DB", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "409", description = "Errors in the Logic Service Flow", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "500", description = "general error service response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            })
    })
    @DeleteMapping("/editorial")
    public GeneralResponseService deleteEditorial(
            @Parameter(name = "uuid", required = true, description = "query param for editorial Entity uuid")
            @RequestParam(name = "uuid") final String uuid) {
        return service.deleteEditorial(uuid);
    }

}

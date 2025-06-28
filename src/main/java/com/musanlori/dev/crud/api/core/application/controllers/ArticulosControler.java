package com.musanlori.dev.crud.api.core.application.controllers;

import com.musanlori.dev.crud.api.core.application.models.request.ArticuloRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.ArticuloResponse;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IArticuloService;
import com.musanlori.dev.crud.api.core.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
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
public class ArticulosControler {

    @Autowired
    private IArticuloService service;

    /**
     * api to create a new Articulo Entity in DDBB.
     * @param request api request object.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to create a new Articulo Entity in DDBB")
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
    @PostMapping("/articulo")
    public GeneralResponseService createArticulo(@RequestBody @Valid final ArticuloRequestModel request) {
        return service.addNewArticulo(request);
    }

    /**
     * api to read all Articulo Entities in DDBB.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to read all Articulos Entities in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ArticuloResponse.class)))
            }),
            @ApiResponse(responseCode = "500", description = "general error service response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            })
    })
    @GetMapping("/articulos")
    public List<ArticuloResponse> readArticulo() {
        return service.getAllArticulos();
    }

    /**
     * api to read an Articulo Entity in DDBB.
     * @param uuid uuid of the Articulo.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to read an Articulo Entity in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ArticuloResponse.class))
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
    @GetMapping("/articulo")
    public ArticuloResponse readArticulo(@RequestParam(name = "uuid") final String uuid) {
        return service.getArticulo(uuid);
    }

    /**
     * api to update an Articulo Entity in DDBB.
     * @param request request object to update.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to update an Articulo Entity in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ArticuloResponse.class))
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
    @PutMapping("/articulo")
    public ArticuloResponse updateArticulo(@RequestBody @Valid final ArticuloRequestModel request) {
        return service.updateArticulo(request);
    }

    /**
     * api to delete an Articulo Entity in DDBB.
     * @param uuid uuid of the Articulo.
     * @return {@link GeneralResponseService} api response object.
     * */
    @Operation(description = "api to delete an Articulo Entity in DDBB")
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
    @DeleteMapping("/articulo")
    public GeneralResponseService deleteArticulo(@RequestParam(name = "uuid") final String uuid) {
        return service.deleteArticulo(uuid);
    }

}

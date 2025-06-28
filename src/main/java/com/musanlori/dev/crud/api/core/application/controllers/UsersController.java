package com.musanlori.dev.crud.api.core.application.controllers;

import com.musanlori.dev.crud.api.core.application.models.request.UserRequest;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.application.models.response.UserResponse;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IUserService;
import com.musanlori.dev.crud.api.core.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = Constants.AUTH_BASE_PATH)
public class UsersController {

    @Autowired
    private IUserService service;

    /**
     * API: get all users in DDBB.
     * @return list of {@link UserResponse}
     * */
    @Operation(description = "get all users in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "general error service response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            })
    })
    @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponse> listAll() {
        return service.findAll();
    }

    /**
     * Api: Creates a new User in DDBB.
     * @param user request for a new user.
     * @param result implicit parameter to validation.
     * @return {@link UserResponse}
     * */
    @Operation(description = "get all users in DDBB")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "success operation", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "general error service response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            }),
            @ApiResponse(responseCode = "500", description = "general error service response", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GeneralResponseService.class))
            })
    })
    @PostMapping(value = "/add-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody final UserRequest user,
                                                   final BindingResult result) {
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

}

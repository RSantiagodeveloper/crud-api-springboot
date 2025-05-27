package com.musanlori.dev.crud.api.core.application.controllers;

import com.musanlori.dev.crud.api.core.application.models.request.UserRequest;
import com.musanlori.dev.crud.api.core.application.models.response.UserResponse;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IUserService;
import com.musanlori.dev.crud.api.core.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/get-all")
    public List<UserResponse> listAll() {
        return service.findAll();
    }

    /**
     * Api: Creates a new User in DDBB.
     * @param user request for a new user.
     * @return {@link UserResponse}
     * */
    @PostMapping("/add-user")
    public ResponseEntity<UserResponse> createUser(@RequestBody final UserRequest user) {
        return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
    }

}

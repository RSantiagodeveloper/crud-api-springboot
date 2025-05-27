package com.musanlori.dev.crud.api.core.domain.services.definitions;

import com.musanlori.dev.crud.api.core.application.models.request.UserRequest;
import com.musanlori.dev.crud.api.core.application.models.response.UserResponse;

import java.util.List;

public interface IUserService {

    /**
     * finds all users.
     * @return List of users
     * */
    List<UserResponse> findAll();


    /**
     * saves a new user object in DDBB.
     *
     * @param user new user to save.
     * @return {@link UserResponse} user saved.
     */
    UserResponse save(UserRequest user);

    /**
     * checks if the username is already exist in DDBB.
     * @param username username to eval.
     * @return boolean.
     * */
    boolean existByUsername(String username);
}

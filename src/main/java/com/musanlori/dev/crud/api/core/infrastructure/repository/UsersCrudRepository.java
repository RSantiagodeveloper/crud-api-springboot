package com.musanlori.dev.crud.api.core.infrastructure.repository;

import com.musanlori.dev.crud.api.core.domain.model.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersCrudRepository extends CrudRepository<UsersEntity, Integer> {

    /**
     * checks if the user entity is already exist in DDBB
     * using its username .
     * @param username username to eval.
     * @return boolean.
     * */
    boolean existsByUsername(String username);

}

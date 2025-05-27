package com.musanlori.dev.crud.api.core.infrastructure.repository;

import com.musanlori.dev.crud.api.core.domain.model.entity.RolesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolesCrudRepository extends CrudRepository<RolesEntity, Integer> {

    /**
     * finds the rol registry using its name.
     * @param name role's name.
     * @return {@link RolesEntity} the role if exists.
     * */
    Optional<RolesEntity> findByName(String name);

}

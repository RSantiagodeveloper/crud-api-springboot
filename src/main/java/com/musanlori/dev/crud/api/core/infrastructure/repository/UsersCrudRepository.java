package com.musanlori.dev.crud.api.core.infrastructure.repository;

import com.musanlori.dev.crud.api.core.domain.model.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersCrudRepository extends CrudRepository<UsersEntity, Integer> {
}

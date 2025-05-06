package com.musanlori.dev.crud.api.core.infrastructure.repository;

import com.musanlori.dev.crud.api.core.domain.model.entity.CatEditorialesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCatEditorialesRepository extends JpaRepository<CatEditorialesEntity, String> {
}

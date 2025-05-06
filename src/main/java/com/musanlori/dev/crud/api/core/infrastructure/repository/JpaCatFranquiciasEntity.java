package com.musanlori.dev.crud.api.core.infrastructure.repository;

import com.musanlori.dev.crud.api.core.domain.model.entity.CatFranquiciasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCatFranquiciasEntity extends JpaRepository<CatFranquiciasEntity, String> {
}

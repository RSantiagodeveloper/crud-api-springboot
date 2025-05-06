package com.musanlori.dev.crud.api.core.infrastructure.repository;

import com.musanlori.dev.crud.api.core.domain.model.entity.CatTiposEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCatTiposRepository extends JpaRepository<CatTiposEntity, Integer> {
}

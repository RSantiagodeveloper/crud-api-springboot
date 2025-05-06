package com.musanlori.dev.crud.api.core.infrastructure.repository;

import com.musanlori.dev.crud.api.core.domain.model.entity.CatFabricantesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCatFabricanteRepository extends JpaRepository<CatFabricantesEntity, String> {
}

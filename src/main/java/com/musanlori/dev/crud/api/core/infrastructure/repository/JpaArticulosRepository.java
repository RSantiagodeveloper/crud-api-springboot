package com.musanlori.dev.crud.api.core.infrastructure.repository;

import com.musanlori.dev.crud.api.core.domain.model.entity.ArticulosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// Aqui se implementan las consultas a partir del JPARepository para la entiti definida
public interface JpaArticulosRepository extends JpaRepository<ArticulosEntity, String> {
}

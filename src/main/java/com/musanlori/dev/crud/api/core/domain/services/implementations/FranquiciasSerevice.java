package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatFranquiciasEntity;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IFranquiciasService;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatFranquiciasEntity;
import com.musanlori.dev.crud.api.core.util.DateConversor;
import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FranquiciasSerevice implements IFranquiciasService {

    @Autowired
    private JpaCatFranquiciasEntity repository;

    private final String entityName = "FRANQUICIA";

    /**
     * {@inheritDoc}
     * */
    @Override
    public GeneralResponseService addNewFranquicia(final CatalogosRequestModel request) {
        log.info("EXECUTING SERVICE ADD_NEW_FRANQUICIA");

        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            log.error("ERROR: OBLIGATORY FIELD NOT VALID OR NOT FOUND");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                    entityName);
        }

        CatFranquiciasEntity entity =  new CatFranquiciasEntity();
        entity.setDescripcion(request.getDescription());
        entity.setCreatedAt(new Date());
        try {
            repository.save(entity);
            return new GeneralResponseService(ErrorServiceMessages.OPERATION_SUCCESS_CODE,
                    ErrorServiceMessages.OPERATION_SUCCESS_MSG);
        } catch (Exception e) {
            log.error("Save Operation Failed. Error: {}", e.getMessage());
            return new GeneralResponseService(
                    ErrorServiceMessages.GRAL_ERROR_CODE,
                    ErrorServiceMessages.GRAL_ERROR_MSG
            );
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public List<CatalogoResponseModel> getAllFranquicias() {
        log.info("EXECUTING SERVICE GET_ALL_FRANQUICIAS");

        List<CatFranquiciasEntity> results = repository.findAll();
        List<CatalogoResponseModel> editoriales = new ArrayList<>();

        for (CatFranquiciasEntity entity: results) {
            editoriales.add(
                    new CatalogoResponseModel(
                            entity.getIdFranquicia(),
                            entity.getDescripcion(),
                            DateConversor.toLocalDateTimeFormat(entity.getCreatedAt()),
                            DateConversor.toLocalDateTimeFormat(entity.getUpdatedAt())
                    )
            );
        }
        return editoriales;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public CatalogoResponseModel getFranquicia(final String uuid) {
        log.info("EXECUTING SERVICE GET_FRANQUICIA");

        if (uuid == null || uuid.isEmpty()) {
            log.error("invalid uuid. read operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG, entityName);
        }

        Optional<CatFranquiciasEntity> result = repository.findById(uuid);

        if (result.isEmpty()) {
            log.error("The entity has not found");
            throw new NotFoundResourceException(ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                    ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG);
        }

        CatFranquiciasEntity entity = result.get();

        return new CatalogoResponseModel(
                entity.getIdFranquicia(),
                entity.getDescripcion(),
                DateConversor.toLocalDateTimeFormat(entity.getCreatedAt()),
                DateConversor.toLocalDateTimeFormat(entity.getUpdatedAt())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatalogoResponseModel updateFranquicia(final CatalogosRequestModel request) {
        log.info("EXECUTING SERVICE UPDATE_FRANQUICIA");

        if (request.getUuidElement() == null || request.getUuidElement().isEmpty()) {
            log.error("ERROR: UUID NOT VALID OR NOT FOUND");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_ID_MSG,
                    entityName);
        }

        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            log.error("ERROR: REQUIRED FIELDS NOT VALID OR NOT FOUND");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                    entityName);
        }

        Optional<CatFranquiciasEntity> queryResult = repository.findById(request.getUuidElement());

        CatFranquiciasEntity reg = queryResult.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE, ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG));

        CatFranquiciasEntity entity =  new CatFranquiciasEntity();
        entity.setIdFranquicia(request.getUuidElement());
        entity.setDescripcion(request.getDescription());
        entity.setCreatedAt(reg.getCreatedAt());
        entity.setUpdatedAt(new Date());

        try {
            CatFranquiciasEntity result = repository.save(entity);
            return new CatalogoResponseModel(
                    result.getIdFranquicia(),
                    result.getDescripcion(),
                    DateConversor.toLocalDateTimeFormat(result.getCreatedAt()),
                    DateConversor.toLocalDateTimeFormat(result.getUpdatedAt())
            );
        } catch (Exception e) {
            log.error("update Operation Failed. Error: {}", e.getMessage());
            throw new LogicServiceConflictException(ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                    ErrorServiceMessages.UPDATE_OPERATION_ERROR_MSG,
                    entityName);
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public GeneralResponseService deleteFranquicia(final String uuid) {
        log.info("EXECUTING SERVICE DELETE_FRANQUICIA");

        if (uuid == null || uuid.isEmpty()) {
            log.error("invalid uuid. delete operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                    entityName);
        }

        Optional<CatFranquiciasEntity> queryResult = repository.findById(uuid);
        queryResult.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE, ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG));

        try {
            repository.deleteById(uuid);
            return new GeneralResponseService(ErrorServiceMessages.OPERATION_SUCCESS_CODE,
                    ErrorServiceMessages.OPERATION_SUCCESS_MSG);
        } catch (Exception e) {
            throw new LogicServiceConflictException(ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                    ErrorServiceMessages.DELETE_OPERATION_ERROR_MSG,
                    entityName);
        }
    }
}

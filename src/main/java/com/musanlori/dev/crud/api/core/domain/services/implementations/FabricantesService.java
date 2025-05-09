package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatFabricantesEntity;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IFabricantesService;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatFabricanteRepository;
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
public class FabricantesService implements IFabricantesService {

    @Autowired
    private JpaCatFabricanteRepository repository;

    private final String entityName = "FABRICANTE";

    /**
     * {@inheritDoc}
     * */
    @Override
    public GeneralResponseService addNewFabricante(final CatalogosRequestModel request) {
        log.info("EXECUTING SERVICE ADD_NEW_FABRICANTE");

        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            log.error("ERROR: OBLIGATORY FIELD NOT VALID OR NOT FOUND");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                    entityName);
        }

        CatFabricantesEntity entity =  new CatFabricantesEntity();
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
    public List<CatalogoResponseModel> getAllFabricantes() {
        log.info("EXECUTING SERVICE GET_ALL_FABRICANTES");

        List<CatFabricantesEntity> results = repository.findAll();
        List<CatalogoResponseModel> editoriales = new ArrayList<>();

        for (CatFabricantesEntity entity: results) {
            editoriales.add(
                    new CatalogoResponseModel(
                            entity.getIdFabricante(),
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
    public CatalogoResponseModel getFabricante(final String uuid) {
        log.info("EXECUTING SERVICE GET_FABRICANTE");

        if (uuid == null || uuid.isEmpty()) {
            log.error("invalid uuid. read operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG, entityName);
        }

        Optional<CatFabricantesEntity> result = repository.findById(uuid);

        if (result.isEmpty()) {
            log.error("The entity has not found");
            throw new NotFoundResourceException(ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                    ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG);
        }

        CatFabricantesEntity entity = result.get();

        return new CatalogoResponseModel(
                entity.getIdFabricante(),
                entity.getDescripcion(),
                DateConversor.toLocalDateTimeFormat(entity.getCreatedAt()),
                DateConversor.toLocalDateTimeFormat(entity.getUpdatedAt())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatalogoResponseModel updateFabricante(final CatalogosRequestModel request) {
        log.info("EXECUTING SERVICE UPDATE_FABRICANTE");

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

        Optional<CatFabricantesEntity> queryResult = repository.findById(request.getUuidElement());

        CatFabricantesEntity reg = queryResult.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE, ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG));

        CatFabricantesEntity entity =  new CatFabricantesEntity();
        entity.setIdFabricante(request.getUuidElement());
        entity.setDescripcion(request.getDescription());
        entity.setCreatedAt(reg.getCreatedAt());
        entity.setUpdatedAt(new Date());

        try {
            CatFabricantesEntity result = repository.save(entity);
            return new CatalogoResponseModel(
                    result.getIdFabricante(),
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
    public GeneralResponseService deleteFabricante(final String uuid) {
        log.info("EXECUTING SERVICE DELETE_FABRICANTE");

        if (uuid == null || uuid.isEmpty()) {
            log.error("invalid uuid. delete operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                    entityName);
        }

        Optional<CatFabricantesEntity> queryResult = repository.findById(uuid);
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

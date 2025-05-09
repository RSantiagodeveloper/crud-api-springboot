package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatEditorialesEntity;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IEditorialesService;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatEditorialesRepository;
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
public class EditorialesService implements IEditorialesService {

    @Autowired
    private JpaCatEditorialesRepository repository;

    private final String entityName = "EDITORIAL";

    /**
     * {@inheritDoc}
     * */
    @Override
    public GeneralResponseService addNewEditorial(final CatalogosRequestModel request) {
        log.info("EXECUTING SERVICE ADD_NEW_EDITORIAL");

        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            log.error("ERROR: OBLIGATORY FIELD NOT VALID OR NOT FOUND");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                                                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                                                    entityName);
        }

        CatEditorialesEntity entity =  new CatEditorialesEntity();
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
    public List<CatalogoResponseModel> getAllEditoriales() {
        log.info("EXECUTING SERVICE GET_ALL_EDITORIALES");

        List<CatEditorialesEntity> results = repository.findAll();
        List<CatalogoResponseModel> editoriales = new ArrayList<>();

        for (CatEditorialesEntity entity: results) {
            editoriales.add(
                    new CatalogoResponseModel(
                            entity.getIdEditorial(),
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
    public CatalogoResponseModel getEditorial(final String uuid) {
        log.info("EXECUTING SERVICE GET_EDITORIAL");

        if (uuid == null || uuid.isEmpty()) {
            log.error("invalid uuid. read operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG, entityName);
        }

        Optional<CatEditorialesEntity> result = repository.findById(uuid);

        if (result.isEmpty()) {
            log.error("The entity has not found");
            throw new NotFoundResourceException(ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                                                ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG);
        }

        CatEditorialesEntity entity = result.get();

        return new CatalogoResponseModel(
                entity.getIdEditorial(),
                entity.getDescripcion(),
                DateConversor.toLocalDateTimeFormat(entity.getCreatedAt()),
                DateConversor.toLocalDateTimeFormat(entity.getUpdatedAt())
        );
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public CatalogoResponseModel updateEditorial(final CatalogosRequestModel request) {
        log.info("EXECUTING SERVICE UPDATE_EDITORIAL");

        if (request.getUuidElement() == null || request.getUuidElement().isEmpty()) {
            log.error("ERROR: UUID NOT VALID OR NOT FOUND");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_ID_MSG,
                    "EDITORIAL");
        }

        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            log.error("ERROR: REQUIRED FIELDS NOT VALID OR NOT FOUND");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                    entityName);
        }

        Optional<CatEditorialesEntity> queryResult = repository.findById(request.getUuidElement());

        CatEditorialesEntity reg = queryResult.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE, ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG));

        CatEditorialesEntity entity =  new CatEditorialesEntity();
        entity.setIdEditorial(request.getUuidElement());
        entity.setDescripcion(request.getDescription());
        entity.setCreatedAt(reg.getCreatedAt());
        entity.setUpdatedAt(new Date());

        try {
            CatEditorialesEntity result = repository.save(entity);
            return new CatalogoResponseModel(
                    result.getIdEditorial(),
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
    public GeneralResponseService deleteEditorial(final String uuid) {
        log.info("EXECUTING SERVICE DELETE_EDITORIAL");

        if (uuid == null || uuid.isEmpty()) {
            log.error("invalid uuid. delete operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                    entityName);
        }

        Optional<CatEditorialesEntity> queryResult = repository.findById(uuid);
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

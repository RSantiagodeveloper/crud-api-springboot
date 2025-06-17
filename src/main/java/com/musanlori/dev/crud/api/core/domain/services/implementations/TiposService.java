package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatTiposEntity;
import com.musanlori.dev.crud.api.core.domain.services.definitions.ITiposService;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatTiposRepository;
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
public class TiposService implements ITiposService {

    @Autowired
    private JpaCatTiposRepository repository;

    private final String entityName = "TIPO";

    /**
     * {@inheritDoc}
     * */
    @Override
    public GeneralResponseService addNewTipo(final CatalogosRequestModel request) {
        log.info("EXECUTING SERVICE ADD_NEW_TIPO");

        CatTiposEntity entity =  new CatTiposEntity();
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
    public List<CatalogoResponseModel> getAllTipos() {
        log.info("EXECUTING SERVICE GET_ALL_TIPOES");

        List<CatTiposEntity> results = repository.findAll();
        List<CatalogoResponseModel> editoriales = new ArrayList<>();

        for (CatTiposEntity entity: results) {
            editoriales.add(
                    new CatalogoResponseModel(
                            entity.getIdTipo().toString(),
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
    public CatalogoResponseModel getTipo(final Integer id) {
        log.info("EXECUTING SERVICE GET_TIPO");

        if (id == null) {
            log.error("invalid uuid. read operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG, entityName);
        }

        Optional<CatTiposEntity> result = repository.findById(id);

        if (result.isEmpty()) {
            log.error("The entity has not found");
            throw new NotFoundResourceException(ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                    ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG);
        }

        CatTiposEntity entity = result.get();

        return new CatalogoResponseModel(
                entity.getIdTipo().toString(),
                entity.getDescripcion(),
                DateConversor.toLocalDateTimeFormat(entity.getCreatedAt()),
                DateConversor.toLocalDateTimeFormat(entity.getUpdatedAt())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CatalogoResponseModel updateTipo(final CatalogosRequestModel request) {
        log.info("EXECUTING SERVICE UPDATE_TIPO");

        if (request.getIdElement() == null) {
            log.error("ERROR: UUID NOT VALID OR NOT FOUND");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_ID_MSG,
                    entityName);
        }

        Optional<CatTiposEntity> queryResult = repository.findById(request.getIdElement());

        CatTiposEntity reg = queryResult.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE, ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG));

        CatTiposEntity entity =  new CatTiposEntity();
        entity.setIdTipo(request.getIdElement());
        entity.setDescripcion(request.getDescription());
        entity.setCreatedAt(reg.getCreatedAt());
        entity.setUpdatedAt(new Date());

        try {
            CatTiposEntity result = repository.save(entity);
            return new CatalogoResponseModel(
                    result.getIdTipo().toString(),
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
    public GeneralResponseService deleteTipo(final Integer id) {
        log.info("EXECUTING SERVICE DELETE_TIPO");

        if (id == null) {
            log.error("invalid uuid. delete operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                    entityName);
        }

        Optional<CatTiposEntity> queryResult = repository.findById(id);
        queryResult.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE, ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG));

        try {
            repository.deleteById(id);
            return new GeneralResponseService(ErrorServiceMessages.OPERATION_SUCCESS_CODE,
                    ErrorServiceMessages.OPERATION_SUCCESS_MSG);
        } catch (Exception e) {
            throw new LogicServiceConflictException(ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                    ErrorServiceMessages.DELETE_OPERATION_ERROR_MSG,
                    entityName);
        }
    }
}

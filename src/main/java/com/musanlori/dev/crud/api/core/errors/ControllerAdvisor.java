package com.musanlori.dev.crud.api.core.errors;

import com.musanlori.dev.crud.api.core.errors.exceptions.ServiceFlowErrorException;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor {

    /**
     * Manejador para RequestDataNotValidException.
     * Genera un response del sercicio cuando ocurre un problema con los datos del request
     * @param ex objeto de excetion que captura
     * @return {@link ResponseEntity<GeneralResponseService>}
     * */
    @ExceptionHandler(RequestDataNotValidException.class)
    public ResponseEntity<GeneralResponseService> requestDataNotValidExceptonHandler(final RequestDataNotValidException ex) {
        log.error(ErrorServiceMessages.SERVICE_EXCEPTION_LOG_MESSAGE, ex.getMessage());
        GeneralResponseService response = new GeneralResponseService(ex.getCode(), ex.getMessage(), ex.getErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manejador para LogicServiceConflictException.
     * Genera un response del sercicio cuando ocurre un problema con la logica de operacion.
     * @param ex objeto de excetion que captura
     * @return {@link ResponseEntity<GeneralResponseService>}
     * */
    @ExceptionHandler(LogicServiceConflictException.class)
    public ResponseEntity<GeneralResponseService> logicServiceConflictExceptionHandler(final LogicServiceConflictException ex) {
        log.error(ErrorServiceMessages.SERVICE_EXCEPTION_LOG_MESSAGE, ex.getMessage());
        GeneralResponseService response = new GeneralResponseService(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Manejador para NotFoundResourceException.
     * Genera un response del sercicio cuando ocurre un problema al localizar un recurso
     * o informacion en DB de la que depende el servicio.
     * @param ex objeto de excetion que captura
     * @return {@link ResponseEntity<GeneralResponseService>}
     * */
    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<GeneralResponseService> notFoundResourceExceptionHandler(final NotFoundResourceException ex) {
        log.error(ErrorServiceMessages.SERVICE_EXCEPTION_LOG_MESSAGE, ex.getMessage());
        GeneralResponseService response = new GeneralResponseService(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Manejador para ServiceFlowErrorException.
     * Genera un response del sercicio cuando ocurre un problema general controlado
     * durante el flujo de operacion del servicio.
     * @param ex objeto de excetion que captura
     * @return {@link ResponseEntity<GeneralResponseService>}
     * */
    @ExceptionHandler(ServiceFlowErrorException.class)
    public ResponseEntity<GeneralResponseService> errorGeneralExceptionHandler(final ServiceFlowErrorException ex) {
        log.error(ErrorServiceMessages.SERVICE_EXCEPTION_LOG_MESSAGE, ex.getMessage());
        GeneralResponseService response = new GeneralResponseService(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejador para Exception.
     * Genera un response del sercicio cuando ocurre un problema general no controlado
     * durante el flujo de operacion del servicio.
     * @param ex objeto de excetion que captura
     * @return {@link ResponseEntity<GeneralResponseService>}
     * */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponseService> genericExceptionHandler(final Exception ex) {
        log.error(ErrorServiceMessages.SERVICE_EXCEPTION_LOG_MESSAGE, ex);
        GeneralResponseService response =
                new GeneralResponseService(ErrorServiceMessages.GRAL_ERROR_CODE, ErrorServiceMessages.GRAL_ERROR_MSG);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

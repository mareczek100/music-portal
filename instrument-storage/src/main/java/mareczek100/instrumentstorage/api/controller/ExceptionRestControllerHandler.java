package mareczek100.instrumentstorage.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionRestControllerHandler extends ResponseEntityExceptionHandler {
    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS = Map.of(
            ConstraintViolationException.class, HttpStatus.BAD_REQUEST,
            DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST,
            InvalidDataAccessApiUsageException.class, HttpStatus.BAD_REQUEST,
            RuntimeException.class, HttpStatus.BAD_REQUEST,
            EntityNotFoundException.class, HttpStatus.NOT_FOUND,
            NotFoundException.class, HttpStatus.NOT_FOUND
    );

    @Override
    protected ResponseEntity<Object> handleExceptionInternal (
            @NonNull Exception ex,
            @Nullable Object body,
            @NonNull HttpHeaders httpHeaders,
            @NonNull HttpStatusCode httpStatusCode,
            @NonNull WebRequest webRequest) {
        final String errorId = UUID.randomUUID().toString();
        log.error("Exception: ID={}, HttpStatus={}", errorId, httpStatusCode, ex);
        return super.handleExceptionInternal(ex, ExceptionMessage.builder().errorId(errorId).build(),
                httpHeaders, httpStatusCode, webRequest);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(final Exception ex) {
        return doHandle(ex, getHttpStatusFromException(ex.getClass()));
    }

    private ResponseEntity<?> doHandle(final Exception ex, final HttpStatus httpStatus){
        final String errorId = UUID.randomUUID().toString();
        log.error("Exception: ID={}, HttpStatus={}", errorId, httpStatus, ex);

        return ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.TEXT_PLAIN)
                .body(ex.getMessage());
    }

    private HttpStatus getHttpStatusFromException(Class<? extends Exception> exceptionClass) {
        return EXCEPTION_STATUS.getOrDefault(exceptionClass, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Builder
    private record ExceptionMessage (String errorId){
    }
}
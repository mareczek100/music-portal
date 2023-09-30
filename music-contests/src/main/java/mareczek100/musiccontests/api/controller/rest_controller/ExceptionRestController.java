package mareczek100.musiccontests.api.controller.rest_controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionRestController extends ResponseEntityExceptionHandler {

    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS = Map.of(
            RuntimeException.class, HttpStatus.BAD_REQUEST,
            ConstraintViolationException.class, HttpStatus.BAD_REQUEST,
            DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST,
            DateTimeParseException.class, HttpStatus.BAD_REQUEST,
            EntityNotFoundException.class, HttpStatus.NOT_FOUND,
            NotFoundException.class, HttpStatus.NOT_FOUND
    );

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            @Nullable Object body,
            @NonNull HttpHeaders httpHeaders,
            @NonNull HttpStatusCode httpStatusCode,
            @NonNull WebRequest webRequest
    )
    {
        final UUID errorId = UUID.randomUUID();
        log.error("Exception: ID={}, HttpStatus={}", errorId, httpStatusCode, ex);
        return super.handleExceptionInternal(ex, ErrorId.builder().errorId(errorId).build(),
                httpHeaders, httpStatusCode, webRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(final Exception ex) {
        return doHandle(ex, getHttpStatusFromException(ex.getClass()));
    }

    private ResponseEntity<Object> doHandle(final Exception ex, final HttpStatus httpStatus) {
        final UUID errorId = UUID.randomUUID();
        log.error("Exception: ID={}, HttpStatus={}", errorId, httpStatus, ex);

        return ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorId.builder()
                        .errorId(errorId)
                        .errorMessage(ex.getMessage())
                        .build());
    }

    private HttpStatus getHttpStatusFromException(Class<? extends Exception> exceptionClass) {
        return EXCEPTION_STATUS.getOrDefault(exceptionClass, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Builder
    private record ErrorId(UUID errorId, String errorMessage) {
    }
}

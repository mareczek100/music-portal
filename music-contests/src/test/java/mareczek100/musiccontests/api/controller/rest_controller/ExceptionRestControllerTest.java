package mareczek100.musiccontests.api.controller.rest_controller;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class ExceptionRestControllerTest {

    @InjectMocks
    private ExceptionRestController exceptionRestController;
    @BeforeEach
    void setUp() {
        Assertions.assertThat(exceptionRestController).isNotNull();
    }
    @Test
    void handleExceptionReturnsErrorIdUUIDNumber() {
        //given
        String errorMessage = "Error message";
        Exception exception = new Exception(errorMessage);

        //when
        ResponseEntity<Object> responseEntity = exceptionRestController.handleException(exception);

        //then
        Assertions.assertThat(responseEntity).asString().contains("errorId");
    }
    @Test
    void handleExceptionReturnsStatusCodeDependsOnException() {
        //given
        ConstraintViolationException exception = new ConstraintViolationException(Collections.emptySet());

        //when
        ResponseEntity<Object> responseEntity = exceptionRestController.handleException(exception);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
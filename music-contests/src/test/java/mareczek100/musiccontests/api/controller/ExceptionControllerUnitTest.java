package mareczek100.musiccontests.api.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.xml.bind.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ExceptionControllerUnitTest {

    @InjectMocks
    private ExceptionController exceptionController;

    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(exceptionController);
    }

    @Test
    void handleException() {
        //given
        Exception exception = new Exception("Error message");
        String errorMessage = String.format("Error occurred: [%s]", exception.getMessage());
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", errorMessage);

        //when
        ModelAndView modelAndView = exceptionController.handleException(exception);

        //then
        Assertions.assertThat(modelAndView).asString().contains(errorMessage);
    }

    @Test
    void handleBindException() {
        //given
        BindException exception = new BindException(new Object(), "name");
        String errorMessage = String.format("Bad input for field: [%s], wrong value: [%s]",
                Optional.ofNullable(exception.getFieldError()).map(FieldError::getField).orElse(null),
                Optional.ofNullable(exception.getFieldError()).map(FieldError::getRejectedValue).orElse(null));
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", errorMessage);

        //when
        ModelAndView modelAndView = exceptionController.handleBindException(exception);

        //then
        Assertions.assertThat(modelAndView).asString().contains(errorMessage);
    }

    @Test
    void handleConstraintViolationException() {
        //given
        ValidationException exception = new ValidationException("Error message");
        String errorMessage = String.format("Validation error: [%s]", exception.getMessage());
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", errorMessage);

        //when
        ModelAndView modelAndView = exceptionController.handleValidationException(exception);

        //then
        Assertions.assertThat(modelAndView).asString().contains(errorMessage);
    }

    @Test
    void handleSQLException() {
        //given
        SQLException exception = new SQLException("Error message");
        String errorMessage = "Error occurred: bad input to database.";
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", errorMessage);

        //when
        ModelAndView modelAndView = exceptionController.handleSQLException(exception);

        //then
        Assertions.assertThat(modelAndView).asString().contains(errorMessage);
    }

    @Test
    void testHandleConstraintViolationException() {
        //given
        ConstraintViolationException exception = new ConstraintViolationException(Collections.emptySet());
        String errorMessage = "Error occurred: bad input.";
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", errorMessage);

        //when
        ModelAndView modelAndView = exceptionController.handleConstraintViolationException(exception);

        //then
        Assertions.assertThat(modelAndView).asString().contains(errorMessage);
    }

    @Test
    void handleNoResourceFound() {
        //given
        EntityNotFoundException exception = new EntityNotFoundException();
        String errorMessage = "Could not find requested resource";
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", errorMessage);

        //when
        ModelAndView modelAndView = exceptionController.handleNoResourceFound(exception);

        //then
        Assertions.assertThat(modelAndView).asString().contains(errorMessage);
    }
}
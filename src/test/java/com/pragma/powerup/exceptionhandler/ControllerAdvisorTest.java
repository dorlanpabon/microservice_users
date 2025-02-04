package com.pragma.powerup.exceptionhandler;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exceptionhandler.ControllerAdvisor;
import com.pragma.powerup.infrastructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

class ControllerAdvisorTest {

    ControllerAdvisor controllerAdvisor = new ControllerAdvisor();

    @Test
    void testHandleNoDataFoundException() {
        NoDataFoundException exception = new NoDataFoundException();
        ResponseEntity<Map<String, String>> result = controllerAdvisor.handleNoDataFoundException(exception);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode(),
                "The status code must be NOT_FOUND");
        Assertions.assertEquals(ExceptionResponse.NO_DATA_FOUND.getMessage(),
                result.getBody().get("message"),
                "The message in the body does not match the expected one");
    }

    @Test
    void testHandleException() {
        Exception exception = new Exception("General exception", new Throwable("General exception"));
        ResponseEntity<Map<String, String>> result = controllerAdvisor.handleException(exception);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode(),
                "The status code must be INTERNAL_SERVER_ERROR");
        Assertions.assertEquals(ExceptionResponse.INTERNAL_SERVER_ERROR.getMessage(),
                result.getBody().get("message"),
                "The message in the body does not match the expected one");
    }

    @Test
    void testDomainException() {
        DomainException exception = new DomainException("General exception");
        ResponseEntity<Map<String, String>> result = controllerAdvisor.handleDomainException(exception);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(),
                "The status code must be INTERNAL_SERVER_ERROR");
        Assertions.assertEquals(ExceptionResponse.INTERNAL_SERVER_ERROR.getMessage(),
                result.getBody().get("message"),
                "The message in the body does not match the expected one");
    }

}

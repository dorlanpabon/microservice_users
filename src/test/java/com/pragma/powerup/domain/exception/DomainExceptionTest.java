package com.pragma.powerup.domain.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DomainExceptionTest {

    @Test
    void testDomainExceptionMessage() {
        String expectedMessage = "Error occurred";

        DomainException exception = new DomainException(expectedMessage);

        Assertions.assertEquals(expectedMessage, exception.getMessage(),
                "The message in the exception must be the same as the one passed in the constructor");
    }

    @Test
    void testDomainExceptionIsRuntimeException() {
        DomainException exception = new DomainException("Some error");

        Assertions.assertTrue(exception instanceof RuntimeException,
                "DomainException must be a RuntimeException");
    }
}

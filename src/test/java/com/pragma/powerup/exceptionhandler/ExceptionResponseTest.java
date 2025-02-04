package com.pragma.powerup.exceptionhandler;

import com.pragma.powerup.infrastructure.exceptionhandler.ExceptionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionResponseTest {

    ExceptionResponse exceptionResponse = ExceptionResponse.NO_DATA_FOUND;

    @Test
    void testGetMessage() {
        String result = exceptionResponse.getMessage();

        Assertions.assertEquals("No data found for the requested petition", result);
    }
}

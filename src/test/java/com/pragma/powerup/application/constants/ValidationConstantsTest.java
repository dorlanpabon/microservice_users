package com.pragma.powerup.application.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationConstantsTest {

    @Test
    void testValidationConstants() {
        assertThrows(IllegalStateException.class, ValidationConstants::new);
    }

}
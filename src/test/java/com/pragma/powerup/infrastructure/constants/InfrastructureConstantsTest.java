package com.pragma.powerup.infrastructure.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfrastructureConstantsTest {

    @Test
    void testInfrastructureConstants() {
        assertThrows(IllegalStateException.class, InfrastructureConstants::new);
    }

}
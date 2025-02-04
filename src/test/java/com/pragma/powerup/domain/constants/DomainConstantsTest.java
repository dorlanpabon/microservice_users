package com.pragma.powerup.domain.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainConstantsTest {

    @Test
    void testDomainConstants() {
        assertThrows(IllegalStateException.class, DomainConstants::new);
    }

}
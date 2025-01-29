package com.pragma.powerup.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RolesEnumTest {

    @Test
    void testRolesValues() {
        RolesEnum[] rolesEnum = RolesEnum.values();

        assertEquals(4, rolesEnum.length);
        assertEquals(RolesEnum.ADMINISTRATOR, rolesEnum[0]);
        assertEquals(RolesEnum.OWNER, rolesEnum[1]);
        assertEquals(RolesEnum.EMPLOYEE, rolesEnum[2]);
        assertEquals(RolesEnum.CLIENT, rolesEnum[3]);
    }

    @Test
    void testValueOf() {
        assertEquals(RolesEnum.ADMINISTRATOR, RolesEnum.valueOf("ADMINISTRATOR"));
        assertEquals(RolesEnum.OWNER, RolesEnum.valueOf("OWNER"));
        assertEquals(RolesEnum.EMPLOYEE, RolesEnum.valueOf("EMPLOYEE"));
        assertEquals(RolesEnum.CLIENT, RolesEnum.valueOf("CLIENT"));
    }

    @Test
    void testInvalidValueOfThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> RolesEnum.valueOf("INVALID_ROLE"));
    }
}

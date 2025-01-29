package com.pragma.powerup.domain.model;

import com.pragma.powerup.domain.enums.RolesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Mock
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName(RolesEnum.ADMINISTRATOR);
        role.setDescription("System administrator");
    }

    @Test
    void getId() {
        assertEquals(1L, role.getId());
    }

    @Test
    void setId() {
        role.setId(2L);
        assertEquals(2L, role.getId());
    }

    @Test
    void getName() {
        assertEquals(RolesEnum.ADMINISTRATOR, role.getName());
    }

    @Test
    void setName() {
        role.setName(RolesEnum.OWNER);
        assertEquals(RolesEnum.OWNER, role.getName());
    }

    @Test
    void getDescription() {
        assertEquals("System administrator", role.getDescription());
    }

    @Test
    void setDescription() {
        role.setDescription("Owner of the system");
        assertEquals("Owner of the system", role.getDescription());
    }
}
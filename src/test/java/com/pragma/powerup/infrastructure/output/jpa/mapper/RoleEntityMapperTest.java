package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.powerup.domain.enums.RolesEnum;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class RoleEntityMapperTest {

    private final RoleEntityMapper roleEntityMapper = Mappers.getMapper(RoleEntityMapper.class);

    @Test
    void testToRole() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName(RolesEnum.ADMINISTRATOR);
        roleEntity.setDescription("Admin role");

        Role role = roleEntityMapper.toRole(roleEntity);

        assertNotNull(role);
        assertEquals(roleEntity.getId(), role.getId());
        assertEquals(roleEntity.getName(), role.getName());
        assertEquals(roleEntity.getDescription(), role.getDescription());
    }

    @Test
    void testToRole_NullInput() {
        Role role = roleEntityMapper.toRole(null);

        assertNull(role);
    }
}

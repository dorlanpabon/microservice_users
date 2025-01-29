package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleJpaAdapterTest {

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private RoleEntityMapper roleEntityMapper;

    @InjectMocks
    private RoleJpaAdapter roleJpaAdapter;

    private RoleEntity roleEntity;
    private Role role;

    @BeforeEach
    void setUp() {
        roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName(RolesEnum.ADMINISTRATOR);
        roleEntity.setDescription("Administrator role");

        role = new Role();
        role.setId(1L);
        role.setName(RolesEnum.ADMINISTRATOR);
        role.setDescription("Administrator role");
    }

    @Test
    void testGetRoleByName_RoleExists() {
        when(roleRepository.findByName(RolesEnum.ADMINISTRATOR)).thenReturn(roleEntity);
        when(roleEntityMapper.toRole(roleEntity)).thenReturn(role);

        Optional<Role> result = roleJpaAdapter.getRoleByName(RolesEnum.ADMINISTRATOR);

        assertTrue(result.isPresent());
        assertEquals(role.getId(), result.get().getId());
        assertEquals(role.getName(), result.get().getName());
        assertEquals(role.getDescription(), result.get().getDescription());

        verify(roleRepository).findByName(RolesEnum.ADMINISTRATOR);
        verify(roleEntityMapper).toRole(roleEntity);
    }

    @Test
    void testGetRoleByName_RoleDoesNotExist() {
        when(roleRepository.findByName(RolesEnum.OWNER)).thenReturn(null);
        when(roleEntityMapper.toRole(null)).thenReturn(null);

        Optional<Role> result = roleJpaAdapter.getRoleByName(RolesEnum.OWNER);

        assertFalse(result.isPresent());

        verify(roleRepository).findByName(RolesEnum.OWNER);
        verify(roleEntityMapper).toRole(null);
    }
}

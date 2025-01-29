package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.infrastructure.output.jpa.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityMapperTest {

    private final UserEntityMapper userEntityMapper = Mappers.getMapper(UserEntityMapper.class);

    @Test
    void testToEntity() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPhone("+123456789");
        user.setPassword("securepassword");

        Role role = new Role();
        role.setId(1L);
        role.setName(RolesEnum.ADMINISTRATOR);
        role.setDescription("Admin role");
        user.setRole(role);

        UserEntity userEntity = userEntityMapper.toEntity(user);

        assertAll("userEntity",
                () -> assertNotNull(userEntity),
                () -> assertEquals(user.getId(), userEntity.getId()),
                () -> assertEquals(user.getFirstName(), userEntity.getFirstName()),
                () -> assertEquals(user.getLastName(), userEntity.getLastName()),
                () -> assertEquals(user.getEmail(), userEntity.getEmail()),
                () -> assertEquals(user.getPhone(), userEntity.getPhone()),
                () -> assertEquals(user.getPassword(), userEntity.getPassword())
        );
    }

    @Test
    void testToEntity_NullInput() {
        UserEntity userEntity = userEntityMapper.toEntity(null);

        assertNull(userEntity);
    }
}

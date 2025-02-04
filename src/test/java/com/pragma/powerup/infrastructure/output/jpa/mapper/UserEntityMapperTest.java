package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.output.jpa.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserEntityMapperTest {

    private final UserEntityMapper userEntityMapper = Mappers.getMapper(UserEntityMapper.class);

    @Mock
    private UserEntity userEntity;

    @Mock
    private RoleEntity roleEntity;

    @Mock
    private User user;

    @Mock
    private Role role;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPhone("+123456789");
        user.setPassword("securepassword");

        role = new Role();
        role.setId(1L);
        role.setName(RolesEnum.ADMINISTRATOR);
        role.setDescription("Admin role");
        user.setRole(role);


        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("test@test.com");
        userEntity.setPhone("+123456789");
        userEntity.setPassword("securepassword");

        roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName(RolesEnum.ADMINISTRATOR);
        roleEntity.setDescription("Admin role");
    }

    @Test
    void testToEntity() {
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

    @Test
    void testToDomain() {
        User user = userEntityMapper.toDomain(userEntity);

        assertAll("user",
                () -> assertNotNull(user),
                () -> assertEquals(userEntity.getId(), user.getId()),
                () -> assertEquals(userEntity.getFirstName(), user.getFirstName()),
                () -> assertEquals(userEntity.getLastName(), user.getLastName()),
                () -> assertEquals(userEntity.getEmail(), user.getEmail()),
                () -> assertEquals(userEntity.getPhone(), user.getPhone()),
                () -> assertEquals(userEntity.getPassword(), user.getPassword())
        );
    }

    @Test
    void testToDomain_NullInput() {
        User user = userEntityMapper.toDomain(null);

        assertNull(user);
    }

}

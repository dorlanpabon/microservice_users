package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.infrastructure.output.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IUserRepository;
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
class UserJpaAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPhone("+123456789");
        user.setPassword("securepassword");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("johndoe@example.com");
        userEntity.setPhone("+123456789");
        userEntity.setPassword("securepassword");
    }

    @Test
    void testExistsByEmail_UserExists() {
        when(userRepository.existsByEmail("johndoe@example.com")).thenReturn(true);

        boolean exists = userJpaAdapter.existsByEmail("johndoe@example.com");

        assertTrue(exists);
        verify(userRepository).existsByEmail("johndoe@example.com");
    }

    @Test
    void testExistsByEmail_UserDoesNotExist() {
        when(userRepository.existsByEmail("notfound@example.com")).thenReturn(false);

        boolean exists = userJpaAdapter.existsByEmail("notfound@example.com");

        assertFalse(exists);
        verify(userRepository).existsByEmail("notfound@example.com");
    }

    @Test
    void testSaveOwnerUser_Success() {
        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        userJpaAdapter.saveUser(user);

        verify(userEntityMapper).toEntity(user);
        verify(userRepository).save(userEntity);
    }

    @Test
    void testExistsByDocumentNumber_UserExists() {
        when(userRepository.existsByDocumentNumber("123456789")).thenReturn(true);

        boolean exists = userJpaAdapter.existsByDocumentNumber("123456789");

        assertTrue(exists);
        verify(userRepository).existsByDocumentNumber("123456789");
    }

    @Test
    void testExistsByDocumentNumber_UserDoesNotExist() {

        when(userRepository.existsByDocumentNumber("987654321")).thenReturn(false);

        boolean exists = userJpaAdapter.existsByDocumentNumber("987654321");

        assertFalse(exists);
        verify(userRepository).existsByDocumentNumber("987654321");
    }

    @Test
    void testGetUserByEmail_UserExists() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.ofNullable(userEntity));
        when(userEntityMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> userByEmail = userJpaAdapter.getUser("test@test.com");

        assertTrue(userByEmail.isPresent());
        assertEquals(user, userByEmail.get());
        verify(userRepository).findByEmail("test@test.com");
        verify(userEntityMapper).toDomain(userEntity);
    }

    @Test
    void testIsOwner_Success() {
        when(userRepository.existsByIdAndRoleId(1L, 1L)).thenReturn(true);

        boolean isOwner = userJpaAdapter.isOwner(1L, 1L);

        assertTrue(isOwner);
        verify(userRepository).existsByIdAndRoleId(1L, 1L);
    }

    @Test
    void testGetPhone_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userEntity));
        when(userEntityMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> userByPhone = userJpaAdapter.getPhone(1L);

        assertTrue(userByPhone.isPresent());
        assertEquals(user, userByPhone.get());
        verify(userRepository).findById(1L);
        verify(userEntityMapper).toDomain(userEntity);
    }

}

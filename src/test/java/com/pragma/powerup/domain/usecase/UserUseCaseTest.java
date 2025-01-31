package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@gmail.com");
        user.setPhone("+123456789");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setDocumentNumber("123456789");
    }

    @Test
    void saveOwnerUser_Success() {
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(anyString())).thenReturn(false);
        when(rolePersistencePort.getRoleByName(any(RolesEnum.class))).thenReturn(Optional.of(new Role()));

        assertDoesNotThrow(() -> userUseCase.saveOwnerUser(user));

        verify(userPersistencePort).saveUser(any(User.class));
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenEmailExists() {
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(true);

        DomainException exception = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(user));

        assertEquals("The email already exists", exception.getMessage());
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenDocumentNumberExists() {
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(anyString())).thenReturn(true);

        DomainException exception = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(user));

        assertEquals("The document number already exists", exception.getMessage());
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenRoleNotFound() {
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(anyString())).thenReturn(false);
        when(rolePersistencePort.getRoleByName(any(RolesEnum.class))).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(user));

        assertEquals("Role not found", exception.getMessage());
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenEmailIsInvalid() {
        user.setEmail("invalid-email");

        DomainException exception = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(user));

        assertEquals("The email is not valid", exception.getMessage());
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenPhoneIsInvalid() {
        user.setPhone("123abc");

        DomainException exception = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(user));

        assertEquals("The phone is not valid", exception.getMessage());
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenUserIsUnderage() {
        user.setBirthDate(LocalDate.now().minusYears(17));

        DomainException exception = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(user));

        assertEquals("The user must be at least 18 years old", exception.getMessage());
    }
}

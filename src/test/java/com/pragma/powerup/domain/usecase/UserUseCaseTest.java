package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @Mock
    private IEmployeeServicePort employeeServicePort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User validOwnerUser;
    private User validEmployeeUser;
    private User validClientUser;
    private Role ownerRole;
    private Role employeeRole;
    private Role clientRole;

    @BeforeEach
    void setUp() {
        validOwnerUser = new User();
        validOwnerUser.setEmail("owner@example.com");
        validOwnerUser.setPhone("1234567890");
        validOwnerUser.setBirthDate(LocalDate.now().minusYears(DomainConstants.MINIMUM_AGE + 5));
        validOwnerUser.setDocumentNumber("DOC123");
        validOwnerUser.setPassword("password");

        validEmployeeUser = new User();
        validEmployeeUser.setEmail("employee@example.com");
        validEmployeeUser.setPhone("0987654321");
        validEmployeeUser.setBirthDate(LocalDate.now().minusYears(25));
        validEmployeeUser.setDocumentNumber("DOC456");
        validEmployeeUser.setPassword("password");

        validClientUser = new User();
        validClientUser.setEmail("client@example.com");
        validClientUser.setPhone("5555555555");
        validClientUser.setBirthDate(LocalDate.now().minusYears(40));
        validClientUser.setDocumentNumber("DOC789");
        validClientUser.setPassword("password");

        ownerRole = new Role();
        ownerRole.setId(1L);
        ownerRole.setName(RolesEnum.OWNER);
        ownerRole.setDescription("Owner role");

        employeeRole = new Role();
        employeeRole.setId(2L);
        employeeRole.setName(RolesEnum.EMPLOYEE);
        employeeRole.setDescription("Employee role");

        clientRole = new Role();
        clientRole.setId(3L);
        clientRole.setName(RolesEnum.CLIENT);
        clientRole.setDescription("Client role");
    }

    @Test
    void saveOwnerUser_Success() {
        when(userPersistencePort.existsByEmail(validOwnerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validOwnerUser.getDocumentNumber())).thenReturn(false);
        when(rolePersistencePort.getRoleByName(RolesEnum.OWNER)).thenReturn(Optional.of(ownerRole));
        when(passwordEncoderPort.encode(validOwnerUser.getPassword())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> userUseCase.saveOwnerUser(validOwnerUser));

        assertEquals(ownerRole, validOwnerUser.getRole());
        assertEquals("encodedPassword", validOwnerUser.getPassword());
        verify(userPersistencePort).saveUser(validOwnerUser);
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenEmailExists() {
        when(userPersistencePort.existsByEmail(validOwnerUser.getEmail())).thenReturn(true);

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(validOwnerUser));
        assertEquals(DomainConstants.EMAIL_ALREADY_EXISTS, ex.getMessage());
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenDocumentNumberExists() {
        when(userPersistencePort.existsByEmail(validOwnerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validOwnerUser.getDocumentNumber())).thenReturn(true);

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(validOwnerUser));
        assertEquals(DomainConstants.DOCUMENT_NUMBER_ALREADY_EXISTS, ex.getMessage());
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenRoleNotFound() {
        when(userPersistencePort.existsByEmail(validOwnerUser.getEmail())).thenReturn(false);
        when(userPersistencePort.existsByDocumentNumber(validOwnerUser.getDocumentNumber())).thenReturn(false);
        when(rolePersistencePort.getRoleByName(RolesEnum.OWNER)).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(validOwnerUser));
        assertEquals(DomainConstants.ROLE_NOT_FOUND, ex.getMessage());
    }

    @Test
    void saveOwnerUser_ThrowsException_WhenUserIsUnderage() {
        validOwnerUser.setBirthDate(LocalDate.now().minusYears(DomainConstants.MINIMUM_AGE - 1));

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.saveOwnerUser(validOwnerUser));
        assertEquals(DomainConstants.USER_UNDERAGE, ex.getMessage());
    }

    @Test
    void isOwner_Success() {
        when(rolePersistencePort.getRoleByName(RolesEnum.OWNER)).thenReturn(Optional.of(ownerRole));
        when(userPersistencePort.isOwner(100L, ownerRole.getId())).thenReturn(true);

        assertDoesNotThrow(() -> userUseCase.isOwner(100L));
    }

    @Test
    void isOwner_ThrowsException_WhenNotOwner() {
        when(rolePersistencePort.getRoleByName(RolesEnum.OWNER)).thenReturn(Optional.of(ownerRole));
        when(userPersistencePort.isOwner(100L, ownerRole.getId())).thenReturn(false);

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.isOwner(100L));
        assertEquals(DomainConstants.USER_NOT_OWNER, ex.getMessage());
    }

    @Test
    void getUser_Success() {
        when(userPersistencePort.getUser("user@example.com")).thenReturn(Optional.of(validClientUser));

        User result = userUseCase.getUser("user@example.com");
        assertEquals(validClientUser, result);
    }

    @Test
    void getUser_ThrowsException_WhenNotFound() {
        when(userPersistencePort.getUser("nonexistent@example.com")).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.getUser("nonexistent@example.com"));
        assertEquals(DomainConstants.USER_NOT_FOUND, ex.getMessage());
    }

    @Test
    void saveEmployeeUser_Success() {
        when(rolePersistencePort.getRoleByName(RolesEnum.EMPLOYEE)).thenReturn(Optional.of(employeeRole));
        when(passwordEncoderPort.encode(validEmployeeUser.getPassword())).thenReturn("encodedEmployee");
        when(userPersistencePort.saveUser(validEmployeeUser)).thenReturn(10L);
        when(employeeServicePort.getUserId()).thenReturn(1L);
        when(employeeServicePort.saveEmployee(10L, 1L)).thenReturn(true);

        assertDoesNotThrow(() -> userUseCase.saveEmployeeUser(validEmployeeUser));

        assertEquals(employeeRole, validEmployeeUser.getRole());
        assertEquals("encodedEmployee", validEmployeeUser.getPassword());
        verify(userPersistencePort).saveUser(validEmployeeUser);
    }

    @Test
    void saveEmployeeUser_ThrowsException_WhenSaveEmployeeFails() {
        when(rolePersistencePort.getRoleByName(RolesEnum.EMPLOYEE)).thenReturn(Optional.of(employeeRole));
        when(passwordEncoderPort.encode(validEmployeeUser.getPassword())).thenReturn("encodedEmployee");
        when(userPersistencePort.saveUser(validEmployeeUser)).thenReturn(10L);
        when(employeeServicePort.getUserId()).thenReturn(1L);
        when(employeeServicePort.saveEmployee(10L, 1L)).thenReturn(false);

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.saveEmployeeUser(validEmployeeUser));
        assertEquals(DomainConstants.EMPLOYEE_ERROR_SAVE, ex.getMessage());
    }

    @Test
    void saveClientUser_Success() {
        when(rolePersistencePort.getRoleByName(RolesEnum.CLIENT)).thenReturn(Optional.of(clientRole));
        when(passwordEncoderPort.encode(validClientUser.getPassword())).thenReturn("encodedClient");
        when(userPersistencePort.saveUser(validClientUser)).thenReturn(20L);

        assertDoesNotThrow(() -> userUseCase.saveClientUser(validClientUser));

        assertEquals(clientRole, validClientUser.getRole());
        assertEquals("encodedClient", validClientUser.getPassword());
        verify(userPersistencePort).saveUser(validClientUser);
    }

    @Test
    void saveClientUser_ThrowsException_WhenEmailInvalid() {
        validClientUser.setEmail("invalid-email");

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.saveClientUser(validClientUser));
        assertEquals(DomainConstants.INVALID_EMAIL, ex.getMessage());
    }

    @Test
    void saveClientUser_ThrowsException_WhenPhoneInvalid() {
        validClientUser.setPhone("abc123");

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.saveClientUser(validClientUser));
        assertEquals(DomainConstants.INVALID_PHONE, ex.getMessage());
    }

    @Test
    void getPhone_Success() {
        when(userPersistencePort.getPhone(100L)).thenReturn((Optional<User>) Optional.of(validOwnerUser));

        String phone = userUseCase.getPhone(100L);
        assertEquals("1234567890", phone);
    }

    @Test
    void getPhone_ThrowsException_WhenNotFound() {
        when(userPersistencePort.getPhone(100L)).thenReturn(Optional.empty());

        DomainException ex = assertThrows(DomainException.class, () -> userUseCase.getPhone(100L));
        assertEquals(DomainConstants.PHONE_NOT_FOUND, ex.getMessage());
    }
}

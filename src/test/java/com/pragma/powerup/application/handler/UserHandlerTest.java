package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.UserClientRequest;
import com.pragma.powerup.application.dto.UserEmployeeRequest;
import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.application.mapper.UserRequestMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @InjectMocks
    private UserHandler userHandler;

    @Mock
    private UserRequestMapper userRequestMapper;

    @Mock
    private IUserServicePort userServicePort;

    private UserOwnerRequest userOwnerRequest;
    private UserClientRequest userClientRequest;
    private UserEmployeeRequest userEmployeeRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userOwnerRequest = new UserOwnerRequest();
        userOwnerRequest.setEmail("test@test.com");
        userOwnerRequest.setPhone("+123456789");
        userOwnerRequest.setBirthDate(LocalDate.of(2000, 1, 1));
        userOwnerRequest.setDocumentNumber("123456789");

        user = new User();
        user.setEmail("test@test.com");
        user.setPhone("+123456789");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setDocumentNumber("123456789L");

        userClientRequest = new UserClientRequest();
        userClientRequest.setEmail("test@test.com");
        userClientRequest.setPhone("+123456789");
        userClientRequest.setDocumentNumber("123456789");
        userClientRequest.setFirstName("Test");
        userClientRequest.setLastName("Test");
        userClientRequest.setPassword("123456");

        userEmployeeRequest = new UserEmployeeRequest();
        userEmployeeRequest.setEmail("test@test.com");
        userEmployeeRequest.setPhone("+123456789");
        userEmployeeRequest.setDocumentNumber("123456789");
        userEmployeeRequest.setFirstName("Test");
        userEmployeeRequest.setLastName("Test");
        userEmployeeRequest.setPassword("123456");
    }

    @Test
    void testSaveUserOwner_ShouldConvertAndSaveUser() {
        when(userRequestMapper.toUser(userOwnerRequest)).thenReturn(user);
        doNothing().when(userServicePort).saveOwnerUser(user);

        assertDoesNotThrow(() -> userHandler.saveUserOwner(userOwnerRequest));


        verify(userRequestMapper, times(1)).toUser(userOwnerRequest);
        verify(userServicePort, times(1)).saveOwnerUser(user);
    }

    @Test
    void testSaveUserOwner_ShouldThrowException_WhenSaveFails() {

        when(userRequestMapper.toUser(userOwnerRequest)).thenReturn(user);
        doThrow(new RuntimeException("Error saving user")).when(userServicePort).saveOwnerUser(user);


        assertThrows(RuntimeException.class, () -> userHandler.saveUserOwner(userOwnerRequest));


        verify(userRequestMapper, times(1)).toUser(userOwnerRequest);
        verify(userServicePort, times(1)).saveOwnerUser(user);
    }

    @Test
    void testGetPhone_ShouldReturnPhone() {
        when(userServicePort.getPhone(1L)).thenReturn("+123456789");
        assertDoesNotThrow(() -> userHandler.getPhone(1L));
        verify(userServicePort, times(1)).getPhone(1L);

        assertEquals("+123456789", userHandler.getPhone(1L));
    }

    @Test
    void testValidateOwner_ShouldReturnTrue() {
        assertDoesNotThrow(() -> userHandler.validateOwner(1L));
        verify(userServicePort, times(1)).isOwner(1L);

        assertDoesNotThrow(() -> userHandler.validateOwner(1L));
    }

    @Test
    void testSaveUserClient_ShouldConvertAndSaveUser() {
        when(userRequestMapper.toUser(userClientRequest)).thenReturn(user);
        doNothing().when(userServicePort).saveClientUser(user);

        assertDoesNotThrow(() -> userHandler.saveUserClient(userClientRequest));
    }

    @Test
    void testSaveUerEmployee_ShouldConvertAndSaveUser() {
        when(userRequestMapper.toUser(userEmployeeRequest)).thenReturn(user);
        doNothing().when(userServicePort).saveEmployeeUser(user);

        assertDoesNotThrow(() -> userHandler.saveUserEmployee(userEmployeeRequest));
    }

    @Test
    void testGetUser_ShouldReturnUser() {
        when(userServicePort.getUser("test@test.com")).thenReturn(user);
        assertDoesNotThrow(() -> userHandler.getUser("test@test.com"));
        verify(userServicePort, times(1)).getUser("test@test.com");
    }

}

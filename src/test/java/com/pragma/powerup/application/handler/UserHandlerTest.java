package com.pragma.powerup.application.handler;

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
    }

    @Test
    void saveUserOwner_ShouldConvertAndSaveUser() {
        when(userRequestMapper.toUser(userOwnerRequest)).thenReturn(user);
        doNothing().when(userServicePort).saveOwnerUser(user);

        assertDoesNotThrow(() -> userHandler.saveUserOwner(userOwnerRequest));


        verify(userRequestMapper, times(1)).toUser(userOwnerRequest);
        verify(userServicePort, times(1)).saveOwnerUser(user);
    }

    @Test
    void saveUserOwner_ShouldThrowException_WhenSaveFails() {

        when(userRequestMapper.toUser(userOwnerRequest)).thenReturn(user);
        doThrow(new RuntimeException("Error saving user")).when(userServicePort).saveOwnerUser(user);


        assertThrows(RuntimeException.class, () -> userHandler.saveUserOwner(userOwnerRequest));


        verify(userRequestMapper, times(1)).toUser(userOwnerRequest);
        verify(userServicePort, times(1)).saveOwnerUser(user);
    }
}

package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.UserClientRequest;
import com.pragma.powerup.application.dto.UserEmployeeRequest;
import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.application.dto.UserResponse;
import com.pragma.powerup.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserRequestMapperTest {

    private final UserRequestMapper userRequestMapper = Mappers.getMapper(UserRequestMapper.class);

    @Mock
    private UserOwnerRequest userOwnerRequest;
    @Mock
    private UserClientRequest userClientRequest;
    @Mock
    private UserEmployeeRequest userEmployeeRequest;
    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        userOwnerRequest = new UserOwnerRequest();
        userOwnerRequest.setFirstName("John");
        userOwnerRequest.setLastName("Doe");
        userOwnerRequest.setDocumentNumber("123456789");
        userOwnerRequest.setPhone("+123456789");
        userOwnerRequest.setBirthDate(LocalDate.of(1990, 5, 15));
        userOwnerRequest.setEmail("test@test.com");
        userOwnerRequest.setPassword("securepassword");

        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDocumentNumber("123456789");
        user.setPhone("+123456789");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        user.setEmail("test@test.com");
        user.setPassword("securepassword");

        userClientRequest = new UserClientRequest();
        userClientRequest.setFirstName("John");
        userClientRequest.setLastName("Doe");
        userClientRequest.setDocumentNumber("123456789");
        userClientRequest.setPhone("+123456789");
        userClientRequest.setEmail("test@test.com");
        userClientRequest.setPassword("securepassword");

        userEmployeeRequest = new UserEmployeeRequest();
        userEmployeeRequest.setFirstName("John");
        userEmployeeRequest.setLastName("Doe");
        userEmployeeRequest.setDocumentNumber("123456789");
        userEmployeeRequest.setPhone("+123456789");
        userEmployeeRequest.setEmail("test@test.com");
        userEmployeeRequest.setPassword("securepassword");
    }

    @Test
    void testToUser() {
        User user = userRequestMapper.toUser(userOwnerRequest);

        assertNotNull(user);
        assertEquals(userOwnerRequest.getFirstName(), user.getFirstName());
        assertEquals(userOwnerRequest.getLastName(), user.getLastName());
        assertEquals(userOwnerRequest.getDocumentNumber(), user.getDocumentNumber());
        assertEquals(userOwnerRequest.getPhone(), user.getPhone());
        assertEquals(userOwnerRequest.getBirthDate(), user.getBirthDate());
        assertEquals(userOwnerRequest.getEmail(), user.getEmail());
        assertEquals(userOwnerRequest.getPassword(), user.getPassword());
    }

    @Test
    void testToDomain() {
        User user = userRequestMapper.toUser(userClientRequest);

        assertNotNull(userClientRequest);
        assertEquals(user.getFirstName(), userClientRequest.getFirstName());
        assertEquals(user.getLastName(), userClientRequest.getLastName());
        assertEquals(user.getDocumentNumber(), userClientRequest.getDocumentNumber());
        assertEquals(user.getPhone(), userClientRequest.getPhone());
        assertEquals(user.getEmail(), userClientRequest.getEmail());
        assertEquals(user.getPassword(), userClientRequest.getPassword());
    }

    @Test
    void testToUserEmployee() {
        User user = userRequestMapper.toUser(userEmployeeRequest);

        assertNotNull(userEmployeeRequest);
        assertEquals(user.getFirstName(), userEmployeeRequest.getFirstName());
        assertEquals(user.getLastName(), userEmployeeRequest.getLastName());
        assertEquals(user.getDocumentNumber(), userEmployeeRequest.getDocumentNumber());
        assertEquals(user.getPhone(), userEmployeeRequest.getPhone());
        assertEquals(user.getEmail(), userEmployeeRequest.getEmail());
        assertEquals(user.getPassword(), userEmployeeRequest.getPassword());
    }

    @Test
    void testToUserResponse() {
        UserResponse userResponse = userRequestMapper.toUserResponse(user);

        assertNotNull(userResponse);
        assertEquals(user.getId(), userResponse.getUserId());
        assertEquals(user.getFirstName(), userResponse.getUserFirstName());
        assertEquals(user.getLastName(), userResponse.getUserLastName());
        assertEquals(user.getDocumentNumber(), userResponse.getUserDocumentNumber());
        assertEquals(user.getPhone(), userResponse.getUserPhone());
        assertEquals(user.getEmail(), userResponse.getUserEmail());
    }

}

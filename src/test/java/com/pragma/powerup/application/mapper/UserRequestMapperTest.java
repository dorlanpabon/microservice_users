package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.domain.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class UserRequestMapperTest {

    private final UserRequestMapper userRequestMapper = Mappers.getMapper(UserRequestMapper.class);

    @Test
    void testToUser() {
        UserOwnerRequest request = new UserOwnerRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDocumentNumber(123456789L);
        request.setPhone("+123456789");
        request.setBirthDate(LocalDate.of(1990, 5, 15));
        request.setEmail("johndoe@example.com");
        request.setPassword("securepassword");

        User user = userRequestMapper.toUser(request);

        assertNotNull(user);
        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getDocumentNumber(), user.getDocumentNumber());
        assertEquals(request.getPhone(), user.getPhone());
        assertEquals(request.getBirthDate(), user.getBirthDate());
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getPassword(), user.getPassword());
    }

    @Test
    void testToUser_NullInput() {
        User user = userRequestMapper.toUser(null);

        assertNull(user);
    }
}

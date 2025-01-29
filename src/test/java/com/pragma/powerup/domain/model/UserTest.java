package com.pragma.powerup.domain.model;

import com.pragma.powerup.domain.enums.RolesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

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
        user.setDocumentNumber(123456789L);
        user.setPhone("+123456789");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        user.setEmail("john.doe@example.com");
        user.setPassword("securepassword");

        role = new Role();
        role.setName(RolesEnum.ADMINISTRATOR);
        role.setDescription("System administrator");
        role.setId(1L);

        user.setRole(role);
    }

    @Test
    void getId() {
        assertEquals(1L, user.getId());
    }

    @Test
    void setId() {
        user.setId(2L);
        assertEquals(2L, user.getId());
    }

    @Test
    void getFirstName() {
        assertEquals("John", user.getFirstName());
    }

    @Test
    void setFirstName() {
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void setLastName() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    void getDocumentNumber() {
        assertEquals(123456789L, user.getDocumentNumber());
    }

    @Test
    void setDocumentNumber() {
        user.setDocumentNumber(987654321L);
        assertEquals(987654321L, user.getDocumentNumber());
    }

    @Test
    void getPhone() {
        assertEquals("+123456789", user.getPhone());
    }

    @Test
    void setPhone() {
        user.setPhone("+987654321");
        assertEquals("+987654321", user.getPhone());
    }

    @Test
    void getBirthDate() {
        assertEquals(LocalDate.of(1990, 5, 15), user.getBirthDate());
    }

    @Test
    void setBirthDate() {
        user.setBirthDate(LocalDate.of(1995, 10, 20));
        assertEquals(LocalDate.of(1995, 10, 20), user.getBirthDate());
    }

    @Test
    void getEmail() {
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void setEmail() {
        user.setEmail("test@test.com");
        assertEquals("test@test.com", user.getEmail());
    }

    @Test
    void getPassword() {
        assertEquals("securepassword", user.getPassword());
    }

    @Test
    void setPassword() {
        user.setPassword("password");
        assertEquals("password", user.getPassword());
    }

    @Test
    void getRole() {
        assertEquals(role, user.getRole());
    }

    @Test
    void setRole() {
        Role newRole = new Role();
        newRole.setName(RolesEnum.OWNER);
        newRole.setDescription("Owner");
        newRole.setId(2L);
        user.setRole(newRole);
        assertEquals(newRole, user.getRole());
    }
}

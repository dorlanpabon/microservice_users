package com.pragma.powerup.application.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Mock
    AuthRequest authRequest;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequest();
        authRequest.setEmail("test@test.com");
        authRequest.setPassword("password");
    }

    @Test
    void getEmail() {
        assertEquals(authRequest.getEmail(), "test@test.com");
    }

    @Test
    void getPassword() {
        assertEquals(authRequest.getPassword(), "password");
    }

    @Test
    void setEmail() {
        authRequest.setEmail("auth@test.com");
        assertEquals(authRequest.getEmail(), "auth@test.com");
    }

    @Test
    void setPassword() {
        authRequest.setPassword("pass");
        assertEquals(authRequest.getPassword(), "pass");
    }
}
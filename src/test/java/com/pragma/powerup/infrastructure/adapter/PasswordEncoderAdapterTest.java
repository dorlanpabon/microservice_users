package com.pragma.powerup.infrastructure.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PasswordEncoderAdapterTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private PasswordEncoderAdapter adapter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(adapter, "passwordEncoder", bCryptPasswordEncoder);
    }

    @Test
    void testEncodeDelegatesToPasswordEncoder() {
        String rawPassword = "mySecretPassword";
        String expectedEncodedPassword = "encodedPassword";

        when(bCryptPasswordEncoder.encode(rawPassword)).thenReturn(expectedEncodedPassword);

        String actualEncodedPassword = adapter.encode(rawPassword);

        verify(bCryptPasswordEncoder, times(1)).encode(rawPassword);
        assertEquals(expectedEncodedPassword, actualEncodedPassword, "El valor codificado debe ser el esperado");
    }

    @Test
    void testEncodeNullThrowsException() {
        when(bCryptPasswordEncoder.encode(null))
                .thenThrow(new IllegalArgumentException("rawPassword cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> adapter.encode(null));
    }
}

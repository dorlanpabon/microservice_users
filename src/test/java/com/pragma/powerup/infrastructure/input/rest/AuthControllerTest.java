package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.AuthRequest;
import com.pragma.powerup.application.dto.AuthResponse;
import com.pragma.powerup.application.dto.UserResponse;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock AuthenticationManager authenticationManager;
    @Mock JwtUtil jwtUtil;
    @Mock UserDetailsService userDetailsService;
    @Mock IUserHandler userHandler;
    @InjectMocks AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignIn() {
        AuthRequest req = new AuthRequest();
        req.setEmail("test@example.com");
        req.setPassword("password");

        UserResponse userRes = new UserResponse();
        userRes.setUserId(1L);

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(() -> "ROLE_USER");
            }
            @Override public String getPassword() { return "password"; }
            @Override public String getUsername() { return "test@example.com"; }
            @Override public boolean isAccountNonExpired() { return true; }
            @Override public boolean isAccountNonLocked() { return true; }
            @Override public boolean isCredentialsNonExpired() { return true; }
            @Override public boolean isEnabled() { return true; }
        };

        when(userHandler.getUser("test@example.com")).thenReturn(userRes);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("test@example.com", "password", userDetails.getAuthorities()));
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails, "ROLE_USER", 1L)).thenReturn("token");

        AuthResponse res = authController.signIn(req);
        assertEquals("token", res.getToken());
    }
}

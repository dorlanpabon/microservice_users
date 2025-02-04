package com.pragma.powerup.infrastructure.client;

import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class FeignClientInterceptorTest {
    @Mock
    HttpServletRequest request;
    @InjectMocks
    FeignClientInterceptor feignClientInterceptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApply() {
        when(request.getHeader(anyString())).thenReturn("getHeaderResponse");

        feignClientInterceptor.apply(new RequestTemplate());
    }
}
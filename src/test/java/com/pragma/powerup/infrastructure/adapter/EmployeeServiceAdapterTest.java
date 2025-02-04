package com.pragma.powerup.infrastructure.adapter;

import com.pragma.powerup.application.dto.UserEmployeeFeignRequest;
import com.pragma.powerup.infrastructure.client.EmployeeFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceAdapterTest {

    @Mock
    EmployeeFeignClient employeeFeignClient;

    @Mock
    HttpServletRequest request;

    @InjectMocks
    EmployeeServiceAdapter employeeServiceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEmployee() {
        boolean result = employeeServiceAdapter.saveEmployee(1L, 1L);
        verify(employeeFeignClient).saveEmployee(any(UserEmployeeFeignRequest.class));
        Assertions.assertTrue(result);
    }

    @Test
    void testSaveEmployee_whenFeignExceptionThrown_returnsFalse() {
        doThrow(mock(FeignException.class))
                .when(employeeFeignClient).saveEmployee(any(UserEmployeeFeignRequest.class));

        boolean result = employeeServiceAdapter.saveEmployee(1L, 1L);

        verify(employeeFeignClient).saveEmployee(any(UserEmployeeFeignRequest.class));
        Assertions.assertFalse(result, "Cuando se lanza FeignException, saveEmployee debe retornar false");
    }

    @Test
    void testGetUserId() {
        when(request.getAttribute(anyString())).thenReturn(1L);
        Long result = employeeServiceAdapter.getUserId();
        Assertions.assertEquals(1L, result);
    }
}

package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.infrastructure.adapter.EmployeeServiceAdapter;
import com.pragma.powerup.infrastructure.adapter.PasswordEncoderAdapter;
import com.pragma.powerup.infrastructure.client.EmployeeFeignClient;
import com.pragma.powerup.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IRoleRepository;
import com.pragma.powerup.infrastructure.output.jpa.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private RoleEntityMapper roleEntityMapper;

    @Mock
    private EmployeeFeignClient employeeFeignClient;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private BeanConfiguration beanConfiguration;

    @Test
    void testRolePersistencePort() {
        IRolePersistencePort result = beanConfiguration.rolePersistencePort();
        assertNotNull(result, "El bean rolePersistencePort no debe ser nulo");
        assertTrue(result instanceof RoleJpaAdapter, "El bean debe ser una instancia de RoleJpaAdapter");

        RoleJpaAdapter roleAdapter = (RoleJpaAdapter) result;
        // Verifica que el repositorio inyectado sea el mock
        assertEquals(roleRepository, ReflectionTestUtils.getField(roleAdapter, "roleRepository"),
                "El roleRepository inyectado no coincide");
        // Verifica que el mapper sea del tipo esperado (por ejemplo, RoleEntityMapperImpl)
        Object mapper = ReflectionTestUtils.getField(roleAdapter, "roleEntityMapper");
        assertNotNull(mapper, "El roleEntityMapper no debe ser nulo");
    }

    @Test
    void testUserPersistencePort() {
        IUserPersistencePort result = beanConfiguration.userPersistencePort();
        assertNotNull(result, "El bean userPersistencePort no debe ser nulo");
        assertTrue(result instanceof UserJpaAdapter, "El bean debe ser una instancia de UserJpaAdapter");

        UserJpaAdapter userAdapter = (UserJpaAdapter) result;
        assertEquals(userRepository, ReflectionTestUtils.getField(userAdapter, "userRepository"),
                "El userRepository inyectado no coincide");

        Object mapper = ReflectionTestUtils.getField(userAdapter, "userEntityMapper");
        assertNotNull(mapper, "El userEntityMapper no debe ser nulo");
    }

    @Test
    void testUserServicePort() {
        PasswordEncoderAdapter passwordEncoder = new PasswordEncoderAdapter();
        IUserServicePort result = beanConfiguration.userServicePort(passwordEncoder);
        assertNotNull(result, "El bean userServicePort no debe ser nulo");
        assertTrue(result instanceof UserUseCase, "El bean debe ser una instancia de UserUseCase");

        UserUseCase userUseCase = (UserUseCase) result;
        Object userPersistence = ReflectionTestUtils.getField(userUseCase, "userPersistencePort");
        assertNotNull(userPersistence, "El userPersistencePort en UserUseCase no debe ser nulo");
        assertTrue(userPersistence instanceof UserJpaAdapter, "userPersistencePort debe ser un UserJpaAdapter");

        Object rolePersistence = ReflectionTestUtils.getField(userUseCase, "rolePersistencePort");
        assertNotNull(rolePersistence, "El rolePersistencePort en UserUseCase no debe ser nulo");
        assertTrue(rolePersistence instanceof RoleJpaAdapter, "rolePersistencePort debe ser un RoleJpaAdapter");

        Object passwordEncoderPort = ReflectionTestUtils.getField(userUseCase, "passwordEncoderPort");
        assertNotNull(passwordEncoderPort, "El passwordEncoderPort en UserUseCase no debe ser nulo");
        assertEquals(passwordEncoder, passwordEncoderPort, "El passwordEncoderPort no coincide con el esperado");

        Object employeeServicePort = ReflectionTestUtils.getField(userUseCase, "employeeServicePort");
        assertNotNull(employeeServicePort, "El employeeServicePort en UserUseCase no debe ser nulo");
        assertTrue(employeeServicePort instanceof EmployeeServiceAdapter,
                "employeeServicePort debe ser una instancia de EmployeeServiceAdapter");
    }

    @Test
    void testEmployeeServicePort() {
        IEmployeeServicePort result = beanConfiguration.employeeServicePort();
        assertNotNull(result, "El bean employeeServicePort no debe ser nulo");
        assertTrue(result instanceof EmployeeServiceAdapter,
                "El bean debe ser una instancia de EmployeeServiceAdapter");

        EmployeeServiceAdapter employeeAdapter = (EmployeeServiceAdapter) result;
        assertEquals(employeeFeignClient, ReflectionTestUtils.getField(employeeAdapter, "employeeFeignClient"),
                "El employeeFeignClient inyectado no coincide");
        assertEquals(request, ReflectionTestUtils.getField(employeeAdapter, "request"),
                "El request inyectado no coincide");
    }
}

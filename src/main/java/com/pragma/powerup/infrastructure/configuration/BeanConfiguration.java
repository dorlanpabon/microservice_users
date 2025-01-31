package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.infrastructure.adapter.EmployeeServiceAdapter;
import com.pragma.powerup.infrastructure.client.EmployeeFeignClient;
import com.pragma.powerup.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IRoleRepository;
import com.pragma.powerup.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final EmployeeFeignClient employeeFeignClient;
    private final HttpServletRequest request;

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort(IPasswordEncoderPort passwordEncoderPort) {
        return new UserUseCase(userPersistencePort(), rolePersistencePort(), passwordEncoderPort, employeeServicePort());
    }

    @Bean
    public IEmployeeServicePort employeeServicePort() {
        return new EmployeeServiceAdapter(employeeFeignClient, request);
    }
}

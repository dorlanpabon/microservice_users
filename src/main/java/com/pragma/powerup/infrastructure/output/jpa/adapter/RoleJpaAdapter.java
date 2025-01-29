package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;

    private final RoleEntityMapper roleEntityMapper;

    @Override
    public Optional<Role> getRoleByName(RolesEnum name) {
        return Optional.ofNullable(roleEntityMapper.toRole(roleRepository.findByName(name)));
    }

}

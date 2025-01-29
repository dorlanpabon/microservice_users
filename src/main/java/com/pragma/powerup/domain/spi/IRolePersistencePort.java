package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.domain.model.Role;

import java.util.Optional;

public interface IRolePersistencePort {

    Optional<Role> getRoleByName(RolesEnum name);

}

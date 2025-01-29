package com.pragma.powerup.infrastructure.output.jpa.repository;

import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(RolesEnum name);

}

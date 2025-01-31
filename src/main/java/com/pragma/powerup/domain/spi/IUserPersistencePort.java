package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {

    boolean existsByEmail(String email);

    Long saveUser(User user);

    boolean existsByDocumentNumber(String documentNumber);

    boolean isOwner(Long userId, Long roleId);

    Optional<User> getUser(String email);
}

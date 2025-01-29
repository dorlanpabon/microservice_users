package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.User;

public interface IUserPersistencePort {

    boolean existsByEmail(String email);

    void saveOwnerUser(User user);

    boolean existsByDocumentNumber(Long documentNumber);
}

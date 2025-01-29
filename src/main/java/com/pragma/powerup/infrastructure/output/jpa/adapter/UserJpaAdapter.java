package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;

    private final UserEntityMapper userEntityMapper;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveOwnerUser(User user) {
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public boolean existsByDocumentNumber(Long documentNumber) {
        return userRepository.existsByDocumentNumber(documentNumber);
    }
}

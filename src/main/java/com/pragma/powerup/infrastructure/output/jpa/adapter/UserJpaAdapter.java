package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;

    private final UserEntityMapper userEntityMapper;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Long saveUser(User user) {
        return userRepository.save(userEntityMapper.toEntity(user)).getId();
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return userRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public boolean isOwner(Long userId, Long roleId) {
        return userRepository.existsByIdAndRoleId(userId, roleId);
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> getPhone(Long userId) {
        return userRepository.findById(userId)
                .map(userEntityMapper::toDomain);
    }
}

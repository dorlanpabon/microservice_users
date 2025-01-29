package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void saveOwnerUser(User user) {
        validateUserOwner(user);

        if (userPersistencePort.existsByEmail(user.getEmail())) {
            throw new DomainException(DomainConstants.EMAIL_ALREADY_EXISTS);
        }

        if (userPersistencePort.existsByDocumentNumber(user.getDocumentNumber())) {
            throw new DomainException(DomainConstants.DOCUMENT_NUMBER_ALREADY_EXISTS);
        }

        Role role = rolePersistencePort.getRoleByName(RolesEnum.OWNER)
                .orElseThrow(() -> new DomainException(DomainConstants.ROLE_NOT_FOUND));
        user.setRole(role);

        userPersistencePort.saveOwnerUser(user);
    }

    private void validateUserOwner(User user) {
        if (!user.getEmail().matches(DomainConstants.EMAIL_REGEX)) {
            throw new DomainException(DomainConstants.INVALID_EMAIL);
        }

        if (!user.getPhone().matches(DomainConstants.PHONE_REGEX)) {
            throw new DomainException(DomainConstants.INVALID_PHONE);
        }

        if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() < DomainConstants.MINIMUM_AGE) {
            throw new DomainException(DomainConstants.USER_UNDERAGE);
        }
    }
}

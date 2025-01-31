package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.enums.RolesEnum;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolePersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final IEmployeeServicePort employeeServicePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort, IPasswordEncoderPort passwordEncoderPort, IEmployeeServicePort employeeServicePort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.employeeServicePort = employeeServicePort;
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

        user.setPassword(passwordEncoderPort.encode(user.getPassword()));

        userPersistencePort.saveUser(user);
    }

    @Override
    public void isOwner(Long userId) {
        Role role = rolePersistencePort.getRoleByName(RolesEnum.OWNER)
                .orElseThrow(() -> new DomainException(DomainConstants.ROLE_NOT_FOUND));

        if (Boolean.FALSE.equals(userPersistencePort.isOwner(userId, role.getId()))) {
            throw new DomainException(DomainConstants.USER_NOT_OWNER);
        }
    }

    @Override
    public User getUser(String email) {
        return userPersistencePort.getUser(email)
                .orElseThrow(() -> new DomainException(DomainConstants.USER_NOT_FOUND));
    }

    @Override
    public void saveEmployeeUser(User user) {
        validateUserEmployee(user);

        Role role = rolePersistencePort.getRoleByName(RolesEnum.EMPLOYEE)
                .orElseThrow(() -> new DomainException(DomainConstants.ROLE_NOT_FOUND));
        user.setRole(role);

        user.setPassword(passwordEncoderPort.encode(user.getPassword()));

        Long employeeId = userPersistencePort.saveUser(user);
        Long ownerId = employeeServicePort.getUserId();

        if (!employeeServicePort.saveEmployee(employeeId, ownerId)) {
            throw new DomainException(DomainConstants.EMPLOYEE_ERROR_SAVE);
        }

    }

    @Override
    public void saveClientUser(User user) {
        validateCommonUserFields(user);

        Role role = rolePersistencePort.getRoleByName(RolesEnum.CLIENT)
                .orElseThrow(() -> new DomainException(DomainConstants.ROLE_NOT_FOUND));
        user.setRole(role);

        user.setPassword(passwordEncoderPort.encode(user.getPassword()));

        userPersistencePort.saveUser(user);
    }

    private void validateUserOwner(User user) {
        validateCommonUserFields(user);
        if (Period.between(user.getBirthDate(), LocalDate.now()).getYears() < DomainConstants.MINIMUM_AGE) {
            throw new DomainException(DomainConstants.USER_UNDERAGE);
        }
    }

    private void validateUserEmployee(User user) {
        validateCommonUserFields(user);
    }

    private void validateCommonUserFields(User user) {
        if (!user.getEmail().matches(DomainConstants.EMAIL_REGEX)) {
            throw new DomainException(DomainConstants.INVALID_EMAIL);
        }
        if (!user.getPhone().matches(DomainConstants.PHONE_REGEX)) {
            throw new DomainException(DomainConstants.INVALID_PHONE);
        }
    }
}

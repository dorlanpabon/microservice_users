package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.UserClientRequest;
import com.pragma.powerup.application.dto.UserEmployeeRequest;
import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.application.dto.UserResponse;

public interface IUserHandler {

    void saveUserOwner(UserOwnerRequest userOwnerRequest);

    void validateOwner(Long userId);

    UserResponse getUser(String email);

    void saveUserEmployee(UserEmployeeRequest userEmployeeRequest);

    void saveUserClient(UserClientRequest userClientRequest);
}

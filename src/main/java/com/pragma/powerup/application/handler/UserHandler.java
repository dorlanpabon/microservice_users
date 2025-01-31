package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.UserClientRequest;
import com.pragma.powerup.application.dto.UserEmployeeRequest;
import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.application.dto.UserResponse;
import com.pragma.powerup.application.mapper.UserRequestMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final UserRequestMapper userRequestMapper;
    private final IUserServicePort userServicePort;

    @Override
    public void saveUserOwner(UserOwnerRequest userOwnerRequest) {
        User user = userRequestMapper.toUser(userOwnerRequest);
        userServicePort.saveOwnerUser(user);
    }

    @Override
    public void validateOwner(Long userId) {
        userServicePort.isOwner(userId);
    }

    @Override
    public UserResponse getUser(String email) {
        User user = userServicePort.getUser(email);
        return userRequestMapper.toUserResponse(user);
    }

    @Override
    public void saveUserEmployee(UserEmployeeRequest userEmployeeRequest) {
        User user = userRequestMapper.toUser(userEmployeeRequest);
        userServicePort.saveEmployeeUser(user);
    }

    @Override
    public void saveUserClient(UserClientRequest userClientRequest) {
        User user = userRequestMapper.toUser(userClientRequest);
        userServicePort.saveClientUser(user);
    }

}

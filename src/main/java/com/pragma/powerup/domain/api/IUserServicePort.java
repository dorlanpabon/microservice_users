package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.User;

public interface IUserServicePort {

    void saveOwnerUser(User user);

    void isOwner(Long userId);

    User getUser(String email);

    void saveEmployeeUser(User user);

    void saveClientUser(User user);

    String getPhone(Long userId);
}

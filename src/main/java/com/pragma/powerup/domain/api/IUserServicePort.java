package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.User;

public interface IUserServicePort {

    void saveOwnerUser(User user);

    void isOwner(Long userId);

    User getUser(String email);
}

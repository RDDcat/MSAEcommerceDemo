package com.maro.userservice.service;

import com.maro.userservice.dto.UserDTO;
import com.maro.userservice.repository.UserEntity;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}

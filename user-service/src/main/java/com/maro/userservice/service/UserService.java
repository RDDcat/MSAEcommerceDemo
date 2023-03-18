package com.maro.userservice.service;

import com.maro.userservice.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
}

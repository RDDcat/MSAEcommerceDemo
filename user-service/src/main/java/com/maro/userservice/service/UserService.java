package com.maro.userservice.service;

import com.maro.userservice.dto.UserDTO;
import com.maro.userservice.repository.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}

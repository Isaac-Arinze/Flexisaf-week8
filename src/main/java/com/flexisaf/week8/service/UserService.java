package com.flexisaf.week8.service;

import com.flexisaf.week8.dto.LoginDto;
import com.flexisaf.week8.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto user);

    void deleteUser(Long userId);

    String loginUser(LoginDto loginDto);  // Return JWT token

    ResponseEntity<String> logout(Authentication authentication);  // Returns ResponseEntity
}

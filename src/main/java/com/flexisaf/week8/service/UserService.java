package com.flexisaf.week7.service;

import com.flexisaf.week7.dto.UserDto;


import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto user);

    void deleteUser (Long userId);

}

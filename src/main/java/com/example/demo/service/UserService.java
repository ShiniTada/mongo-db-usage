package com.example.demo.service;


import com.example.demo.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(long userId);

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user);

    void deleteUser(long userId);

    UserDto getByEmail(String email);
}


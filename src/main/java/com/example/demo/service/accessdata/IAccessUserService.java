package com.example.demo.service.accessdata;


import com.example.demo.dto.UserDto;

import java.util.List;

public interface IAccessUserService {
    UserDto getUserById(String userId);

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user);

    void deleteUser(String userId);

    UserDto getByEmail(String email);
}


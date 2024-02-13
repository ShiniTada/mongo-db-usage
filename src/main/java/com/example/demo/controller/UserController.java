package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.accessdata.IAccessUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final IAccessUserService accessUserService;

    public UserController(@Qualifier("AccessUserService") IAccessUserService accessUserService) {
        this.accessUserService = accessUserService;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return accessUserService.getUserById(id);
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) String email) {
        if (email != null && !email.isEmpty()) {
            return List.of(accessUserService.getByEmail(email));
        }
        return accessUserService.getAllUsers();
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return accessUserService.createUser(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        userDto.setId(String.valueOf(id));
        return accessUserService.updateUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        accessUserService.deleteUser(id);
    }
}

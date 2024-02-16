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
@RequestMapping("/api/v2/users")
public class UserDocController {

    private final IAccessUserService accessUserService;

    public UserDocController(@Qualifier("AccessUserDocService") IAccessUserService accessUserService) {
        this.accessUserService = accessUserService;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable String id) {
        return accessUserService.getById(id);
    }
    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) String email) {
        if (email != null && !email.isEmpty()) {
            return List.of(accessUserService.getByEmail(email));
        }
        return accessUserService.getAll();
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return accessUserService.create(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return accessUserService.update(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        accessUserService.delete(id);
    }
}

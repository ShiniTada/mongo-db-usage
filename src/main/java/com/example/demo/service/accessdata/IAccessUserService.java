package com.example.demo.service.accessdata;


import com.example.demo.dto.UserDto;
import com.example.demo.service.accessdata.IAccessService;

import java.util.List;

public interface IAccessUserService extends IAccessService<UserDto> {

    UserDto getByEmail(String email);
}


package com.example.demo.service.mapper.impl;

import com.example.demo.data.Certificate;
import com.example.demo.data.User;
import com.example.demo.dto.CertificateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    private final Mapper<Certificate, CertificateDto> certificateDocMapper;

    public UserMapper(Mapper<Certificate, CertificateDto> certificateDocMapper) {
        this.certificateDocMapper = certificateDocMapper;
    }

    @Override
    public UserDto mapModelToDto(User model) {
        return new UserDto(model.getId(), model.getName(), model.getEmail(), mapCertificatesToDto(model.getCertificates()));
    }

    @Override
    public User mapDtoToModel(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    private List<CertificateDto> mapCertificatesToDto(List<Certificate> certificates) {
        return certificates.stream().map(certificateDocMapper::mapModelToDto).toList();
    }
}

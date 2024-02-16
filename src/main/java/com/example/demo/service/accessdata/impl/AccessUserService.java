package com.example.demo.service.accessdata.impl;

import com.example.demo.data.Certificate;
import com.example.demo.data.MigrationStatus;
import com.example.demo.data.User;
import com.example.demo.dto.CertificateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.accessdata.IAccessUserService;
import com.example.demo.service.mapper.IMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("AccessUserService")
public class AccessUserService implements IAccessUserService {

    private final UserRepository userRepository;
    private final IMapper<User, UserDto> userMapper;
    private final IMapper<Certificate, CertificateDto> certificateMapper;

    public AccessUserService(UserRepository userRepository, IMapper<User, UserDto> userMapper, IMapper<Certificate, CertificateDto> certificateMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public UserDto getById(String userId) {
        return userRepository.findById(Long.parseLong(userId))
                .map(userMapper::mapModelToDto)
                .orElseThrow(() -> new RuntimeException("No user with ID: " + userId));
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().forEach(u -> userDtoList.add(userMapper.mapModelToDto(u)));
        return userDtoList;
    }

    @Override
    public UserDto create(UserDto dto) {
        User user = userMapper.mapDtoToModel(dto);
        user = attachCertificatesToUser(user, dto.getCertificates());
        user.setMigrationStatus(MigrationStatus.NEW);
        User savedUser = userRepository.save(user);
        return userMapper.mapModelToDto(savedUser);
    }

    @Override
    public UserDto update(UserDto dto) {
        try {
            Long userId = Long.parseLong(dto.getId());
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("No user with ID: " + userId));
            user.getCertificates().clear();
            user.setId(Long.parseLong(dto.getId()));
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user = attachCertificatesToUser(user, dto.getCertificates());
            user.setMigrationStatus(MigrationStatus.NEW);
            User updatedUser = userRepository.save(user);
            return userMapper.mapModelToDto(updatedUser);
        } catch (Exception e) {
            throw new RuntimeException("Can not to update an user: " + dto, e);
        }
    }

    @Override
    public void delete(String id) {
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if (user.isEmpty()) {
            return;
        }
        user.get().getCertificates().forEach(cert -> userRepository.deleteById(cert.getId()));
        userRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public UserDto getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::mapModelToDto)
                .orElseThrow(() -> new RuntimeException("No user with email: " + email));
    }

    private User attachCertificatesToUser(User user, List<CertificateDto> certificateDtos) {
        certificateDtos.stream()
                .map(certificateMapper::mapDtoToModel)
                .peek(cert -> cert.setUser(user))
                .peek(cert -> cert.setMigrationStatus(MigrationStatus.NEW))
                .forEach(cert -> user.getCertificates().add(cert));
        return user;
    }
}
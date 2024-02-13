package com.example.demo.service.impl;

import com.example.demo.data.Certificate;
import com.example.demo.data.User;
import com.example.demo.dto.CertificateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper<User, UserDto> userMapper;
    private final Mapper<Certificate, CertificateDto> certificateMapper;

    public UserServiceImpl(UserRepository userRepository, Mapper<User, UserDto> userMapper, Mapper<Certificate, CertificateDto> certificateMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public UserDto getUserById(long userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapModelToDto)
                .orElseThrow(() -> new RuntimeException("No user with ID: " + userId));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        userRepository.findAll().forEach(u -> userDtoList.add(userMapper.mapModelToDto(u)));
        return userDtoList;
    }

    @Override
    public UserDto createUser(UserDto dto) {
        User user = userMapper.mapDtoToModel(dto);
        user = attachCertificatesToUser(user, dto.getCertificates());
        User savedUser = userRepository.save(user);
        return userMapper.mapModelToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto dto) {
        try {
            Long userId = dto.getId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("No user with ID: " + userId));
            user.getCertificates().clear();
            user.setId(dto.getId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user = attachCertificatesToUser(user, dto.getCertificates());
            User updatedUser = userRepository.save(user);
            return userMapper.mapModelToDto(updatedUser);
        } catch (Exception e) {
            throw new RuntimeException("Can not to update an user: " + dto, e);
        }
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return;
        }
        user.get().getCertificates().forEach(cert -> userRepository.deleteById(cert.getId()));
        userRepository.deleteById(id);
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
                .forEach(cert -> user.getCertificates().add(cert));
        return user;
    }
}
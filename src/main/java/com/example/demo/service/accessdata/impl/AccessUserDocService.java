package com.example.demo.service.accessdata.impl;

import com.example.demo.data.Certificate;
import com.example.demo.document.CertificateDoc;
import com.example.demo.document.UserDoc;
import com.example.demo.dto.CertificateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.repository.UserDocRepository;
import com.example.demo.service.accessdata.IAccessUserService;
import com.example.demo.service.mapper.IMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("AccessUserDocService")
public class AccessUserDocService implements IAccessUserService {
    private final UserDocRepository repository;
    private final IMapper<UserDoc, UserDto> userMapper;
    private final IMapper<CertificateDoc, CertificateDto> certificateMapper;

    public AccessUserDocService(UserDocRepository userDocRepository, IMapper<UserDoc, UserDto> userMapper, IMapper<CertificateDoc, CertificateDto> certificateMapper) {
        this.repository = userDocRepository;
        this.userMapper = userMapper;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public UserDto getUserById(String userId) {
        Optional<UserDoc> user = repository.findById(userId);
        if (user.isEmpty() && isLong(userId)) {
            user = repository.findByUserId(Long.parseLong(userId));
        }
        return user
                .map(userMapper::mapModelToDto)
                .orElseThrow(() -> new RuntimeException("No user with ID: " + userId));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll().stream()
                .map(userMapper::mapModelToDto)
                .toList();
    }

    @Override
    public UserDto createUser(UserDto dto) {
        UserDoc user = userMapper.mapDtoToModel(dto);
        UserDoc savedUser = repository.save(user);
        return userMapper.mapModelToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto dto) {
        try {
            String id = dto.getId();
            UserDoc user = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No user with ID: " + id));
            user.getCertificates().clear();
            user.setId(dto.getId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            List<CertificateDoc> certificates = dto.getCertificates().stream().map(certificateMapper::mapDtoToModel).toList();
            user.setCertificates(certificates);
            UserDoc updatedUser = repository.save(user);
            return userMapper.mapModelToDto(updatedUser);
        } catch (Exception e) {
            throw new RuntimeException("Can not to update an user: " + dto, e);
        }
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserDoc> user = repository.findById(userId);
        if (user.isPresent()) {
            repository.deleteById(userId);
            return;
        }
        if (isLong(userId)) {
            long longId = Long.parseLong(userId);
            user = repository.findByUserId(longId);
            if (user.isPresent()) {
                repository.deleteByUserId(longId);
            }
        }
    }

    @Override
    public UserDto getByEmail(String email) {
        return repository.findByEmail(email)
                .map(userMapper::mapModelToDto)
                .orElseThrow(() -> new RuntimeException("No user with email: " + email));
    }

    private boolean isLong(String str) {
        try {
            long value = Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

package com.example.demo.service.mapper.impl;

import com.example.demo.document.CertificateDoc;
import com.example.demo.document.UserDoc;
import com.example.demo.dto.CertificateDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.mapper.IMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDocMapper implements IMapper<UserDoc, UserDto> {

    private final IMapper<CertificateDoc, CertificateDto> certificateDocMapper;

    public UserDocMapper(IMapper<CertificateDoc, CertificateDto> certificateDocMapper) {
        this.certificateDocMapper = certificateDocMapper;
    }

    @Override
    public UserDto mapModelToDto(UserDoc model) {
        return new UserDto(model.getId(), model.getName(), model.getEmail(), mapCertificatesToDto(model.getCertificates()));
    }

    @Override
    public UserDoc mapDtoToModel(UserDto dto) {
        UserDoc userDoc = new UserDoc();
        userDoc.setId(dto.getId());
        userDoc.setName(dto.getName());
        userDoc.setEmail(dto.getEmail());
        userDoc.setCertificates(mapDtoCertificatesToModel(dto.getCertificates()));
        return userDoc;
    }

    private List<CertificateDto> mapCertificatesToDto(List<CertificateDoc> certificates) {
        return certificates.stream().map(certificateDocMapper::mapModelToDto).toList();
    }

    private List<CertificateDoc> mapDtoCertificatesToModel(List<CertificateDto> certificateDtos) {
        return certificateDtos.stream().map(certificateDocMapper::mapDtoToModel).toList();
    }
}

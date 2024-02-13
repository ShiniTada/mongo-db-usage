package com.example.demo.service.mapper.impl;

import com.example.demo.data.Certificate;
import com.example.demo.dto.CertificateDto;
import com.example.demo.service.mapper.IMapper;
import org.springframework.stereotype.Component;

@Component
public class CertificateMapper implements IMapper<Certificate, CertificateDto> {
    @Override
    public CertificateDto mapModelToDto(Certificate model) {
        return new CertificateDto(model.getTitle());
    }

    @Override
    public Certificate mapDtoToModel(CertificateDto dto) {
        Certificate certificate = new Certificate();
        certificate.setTitle(dto.title());
        return certificate;
    }
}

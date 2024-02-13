package com.example.demo.service.mapper.impl;

import com.example.demo.document.CertificateDoc;
import com.example.demo.dto.CertificateDto;
import com.example.demo.service.mapper.IMapper;
import org.springframework.stereotype.Component;

@Component
public class CertificateDocMapper implements IMapper<CertificateDoc, CertificateDto> {
    @Override
    public CertificateDto mapModelToDto(CertificateDoc model) {
        return new CertificateDto(model.getTitle());
    }

    @Override
    public CertificateDoc mapDtoToModel(CertificateDto dto) {
        CertificateDoc certificateDoc = new CertificateDoc();
        certificateDoc.setTitle(dto.title());
        return certificateDoc;
    }
}

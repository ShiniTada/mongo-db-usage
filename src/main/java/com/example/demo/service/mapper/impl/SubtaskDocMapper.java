package com.example.demo.service.mapper.impl;

import com.example.demo.document.SubtaskDoc;
import com.example.demo.dto.SubtaskDto;
import com.example.demo.service.mapper.IMapper;
import org.springframework.stereotype.Component;

@Component
public class SubtaskDocMapper implements IMapper<SubtaskDoc, SubtaskDto> {
    @Override
    public SubtaskDto mapModelToDto(SubtaskDoc model) {
        return new SubtaskDto(model.getName(), model.getDescription());
    }

    @Override
    public SubtaskDoc mapDtoToModel(SubtaskDto dto) {
        return new SubtaskDoc(dto.name(), dto.description());
    }
}

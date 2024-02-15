package com.example.demo.service.mapper.impl;

import com.example.demo.document.SubtaskDoc;
import com.example.demo.document.TaskDoc;
import com.example.demo.dto.SubtaskDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.service.mapper.IMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskDocMapper implements IMapper<TaskDoc, TaskDto> {

    private final IMapper<SubtaskDoc, SubtaskDto> subtaskDocMapper;

    public TaskDocMapper(IMapper<SubtaskDoc, SubtaskDto> subtaskDocMapper) {
        this.subtaskDocMapper = subtaskDocMapper;
    }

    @Override
    public TaskDto mapModelToDto(TaskDoc model) {
        List<SubtaskDto> subtaskDtos = model.getSubtasks().stream().map(subtaskDocMapper::mapModelToDto).toList();
        return new TaskDto(model.getId(), model.getName(), model.getDescription(), model.getDateOfCreation(),
                model.getDeadline(), subtaskDtos, model.getCategory());
    }

    @Override
    public TaskDoc mapDtoToModel(TaskDto dto) {
        List<SubtaskDoc> subtasks = dto.getSubtasks().stream().map(subtaskDocMapper::mapDtoToModel).toList();
        return new TaskDoc(dto.getId(), dto.getName(), dto.getDescription(), dto.getDateOfCreation(),
                dto.getDeadline(), subtasks, dto.getCategory());
    }
}

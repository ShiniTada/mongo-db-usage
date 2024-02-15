package com.example.demo.service.accessdata.impl;

import com.example.demo.document.SubtaskDoc;
import com.example.demo.document.TaskDoc;
import com.example.demo.dto.SubtaskDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.repository.TaskDocRepository;
import com.example.demo.service.accessdata.IAccessTaskService;
import com.example.demo.service.mapper.IMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccessTaskDocService implements IAccessTaskService {

    private final TaskDocRepository repository;
    private final IMapper<TaskDoc, TaskDto> taskMapper;

    private final IMapper<SubtaskDoc, SubtaskDto> subtaskMapper;

    public AccessTaskDocService(TaskDocRepository repository, IMapper<TaskDoc, TaskDto> taskMapper, IMapper<SubtaskDoc, SubtaskDto> subtaskMapper) {
        this.repository = repository;
        this.taskMapper = taskMapper;
        this.subtaskMapper = subtaskMapper;
    }

    @Override
    public TaskDto getById(String id) {
        return repository.findById(id)
                .map(taskMapper::mapModelToDto)
                .orElseThrow(() -> new RuntimeException("No user with ID: " + id));
    }

    @Override
    public List<TaskDto> getAll() {
        return repository.findAll().stream()
                .map(taskMapper::mapModelToDto)
                .toList();
    }

    @Override
    public TaskDto create(TaskDto dto) {
        TaskDoc task = taskMapper.mapDtoToModel(dto);
        TaskDoc savedTask = repository.save(task);
        return taskMapper.mapModelToDto(savedTask);
    }

    @Override
    public TaskDto update(TaskDto dto) {
        try {
            repository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("No task with ID: " + dto.getId()));
            TaskDoc task = taskMapper.mapDtoToModel(dto);
            TaskDoc updatedTask = repository.save(task);
            return taskMapper.mapModelToDto(updatedTask);
        } catch (Exception e) {
            throw new RuntimeException("Cannot to update task: " + dto, e);
        }
    }

    @Override
    public void delete(String id) {
        Optional<TaskDoc> task = repository.findById(id);
        if (task.isEmpty()) {
            return;
        }
        repository.deleteById(id);
    }

    @Override
    public TaskDto updateSubtasks(String taskId, List<SubtaskDto> subtaskDtos) {
        TaskDoc task = repository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("No task with ID: " + taskId));
        List<SubtaskDoc> subtasks = subtaskDtos.stream().map(subtaskMapper::mapDtoToModel).toList();
        task.getSubtasks().clear();
        task.setSubtasks(subtasks);
        TaskDoc updatedTask = repository.save(task);
        return taskMapper.mapModelToDto(updatedTask);
    }

    @Override
    public List<TaskDto> getByOverdue(boolean isOverdue) {
        LocalDate localDate = LocalDate.now();
        Date today = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<TaskDoc> tasks = new ArrayList<>();
        if (isOverdue) {
            tasks = repository.findByDeadlineBefore(today);
        } else {
            tasks = repository.findByDeadlineLessThanEqual(today);
        }
        return tasks.stream()
                .map(taskMapper::mapModelToDto)
                .toList();
    }

    @Override
    public List<TaskDto> getByCategory(String category) {
        return repository.findByCategory(category).stream()
                .map(taskMapper::mapModelToDto)
                .toList();
    }

    @Override
    public List<SubtaskDto> getSubtasksByTasksCategory(String category) {
        return getByCategory(category).stream()
                .flatMap(task -> task.getSubtasks().stream())
                .distinct()
                .toList();
    }

    @Override
    public List<TaskDto> fullSearch(String value) {
        return repository.fullTextSearch(value).stream()
                .map(taskMapper::mapModelToDto)
                .toList();
    }
}

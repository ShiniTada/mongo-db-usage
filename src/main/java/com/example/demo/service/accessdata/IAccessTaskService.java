package com.example.demo.service.accessdata;

import com.example.demo.dto.SubtaskDto;
import com.example.demo.dto.TaskDto;

import java.util.List;

public interface IAccessTaskService extends IAccessService<TaskDto> {

    TaskDto updateSubtasks(String taskId, List<SubtaskDto> subtaskDtos);

    List<TaskDto> getByOverdue(boolean isOverdue);

    List<TaskDto> getByCategory(String category);

    List<SubtaskDto> getSubtasksByTasksCategory(String category);

    List<TaskDto> fullSearch(String value);
}

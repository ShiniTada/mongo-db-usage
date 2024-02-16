package com.example.demo.controller;

import com.example.demo.dto.SubtaskDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.service.accessdata.IAccessTaskService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/tasks")
public class TaskDocController {

    private final IAccessTaskService accessTaskService;

    public TaskDocController(IAccessTaskService accessTaskService) {
        this.accessTaskService = accessTaskService;
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable String id) {
        return accessTaskService.getById(id);
    }

    /***
     * 1. Display on console all tasks.
     * 2. Display overdue tasks.
     * 3. Display all tasks with a specific category
     * @param isOverdue and @param category work separately
     */
    @GetMapping
    public List<TaskDto> getTasks(@RequestParam(required = false) Boolean isOverdue, @RequestParam(required = false) String category) {
        if (isOverdue != null) {
           return accessTaskService.getByOverdue(isOverdue);
        }
        if (category != null && !category.isEmpty()) {
            return accessTaskService.getByCategory(category);
        }
        return accessTaskService.getAll();
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return accessTaskService.create(taskDto);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable String id, @RequestBody TaskDto taskDto) {
        taskDto.setId(id);
        return accessTaskService.update(taskDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        accessTaskService.delete(id);
    }

    @PutMapping("/{id}/subtasks")
    public TaskDto updateSubtasks(@PathVariable String id, @RequestBody List<SubtaskDto> subtaskDtos) {
        return accessTaskService.updateSubtasks(id, subtaskDtos);
    }

    @GetMapping("/subtasks")
    public List<SubtaskDto> getSubtasksByTasksCategory(@RequestParam String category) {
        return accessTaskService.getSubtasksByTasksCategory(category);
    }

    @GetMapping("/full-search")
    public List<TaskDto> getTasksByFullSearch(@RequestParam String value) {
        return accessTaskService.fullSearch(value);
    }
}

package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String id;
    private String name;
    private String description;
    private Date dateOfCreation;
    private Date deadline;
    private List<SubtaskDto> subtasks;
    private String category;
}

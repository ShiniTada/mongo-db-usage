package com.example.demo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks")
public class TaskDoc {
    @Id
    private String id;
    private String name;
    private String description;
    private Date dateOfCreation;
    private Date deadline;
    private List<SubtaskDoc> subtasks;
    private String category;
}

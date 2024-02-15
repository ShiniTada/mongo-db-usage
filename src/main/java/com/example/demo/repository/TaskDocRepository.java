package com.example.demo.repository;

import com.example.demo.document.TaskDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface TaskDocRepository extends MongoRepository<TaskDoc, String> {

    List<TaskDoc> findByDeadlineBefore(Date date);

    List<TaskDoc> findByDeadlineLessThanEqual(Date date);

    List<TaskDoc> findByCategory(String category);

    // create index in mongo:
    // db.tasks.createIndex( { name: "text", description: "text", "subtasks.text": "text", category: "text" } )
    @Query("{$text: {$search: '?0'}}")
    List<TaskDoc> fullTextSearch(String value);
}

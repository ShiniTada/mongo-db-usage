package com.example.demo.repository;

import com.example.demo.document.UserDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserDocRepository extends MongoRepository<UserDoc, String> {

    Optional<UserDoc> findByUserId(long userId);
    Optional<UserDoc> findByEmail(String email);

    void deleteByUserId(long userId);
}

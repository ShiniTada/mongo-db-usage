package com.example.demo.repository;

import com.example.demo.data.MigrationStatus;
import com.example.demo.data.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByMigrationStatus(MigrationStatus migrationStatus);
}

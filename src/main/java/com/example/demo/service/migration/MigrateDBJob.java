package com.example.demo.service.migration;

import com.example.demo.data.MigrationStatus;
import com.example.demo.data.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MigrateDBJob {
    private final UserRepository userRepository;
    private final UserMigrationService userMigrationService;

    public MigrateDBJob(UserRepository userRepository, UserMigrationService userMigrationService) {
        this.userRepository = userRepository;
        this.userMigrationService = userMigrationService;
    }

    @Scheduled(fixedDelay = 300000) //300000 - 5 minutes, 5000 - 5 seconds
    public void migrate() {
        List<User> users = userRepository.findByMigrationStatus(MigrationStatus.NEW);
        if (!users.isEmpty()) {
            userMigrationService.migrateUsers(users);
            log.info("Users migration is done");
        }
    }

}

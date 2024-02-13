package com.example.demo.service.migration;

import com.example.demo.data.Certificate;
import com.example.demo.data.MigrationStatus;
import com.example.demo.data.User;
import com.example.demo.document.CertificateDoc;
import com.example.demo.document.UserDoc;
import com.example.demo.repository.UserDocRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMigrationService {
    private final UserRepository userRepository;
    private final UserDocRepository userDocRepository;

    public UserMigrationService(UserRepository userRepository, UserDocRepository userDocRepository) {
        this.userRepository = userRepository;
        this.userDocRepository = userDocRepository;
    }

    @Transactional
    public void migrateUsers(List<User> users) {
        List<UserDoc> userDocs = users.stream().map(this::mapUserToUserDoc).toList();
        userDocRepository.saveAll(userDocs);

        for (User user : users) {
            user.setMigrationStatus(MigrationStatus.MIGRATED);
            user.getCertificates().forEach(c -> c.setMigrationStatus(MigrationStatus.MIGRATED));
        }
        userRepository.saveAll(users);
    }

    private UserDoc mapUserToUserDoc(User user) {
        UserDoc userDoc = new UserDoc();
        userDoc.setUserId(user.getId());
        userDoc.setName(user.getName());
        userDoc.setEmail(user.getEmail());
        userDoc.setCertificates(mapCertificatesToCertificateDocs(user.getCertificates()));
        return userDoc;
    }

    private List<CertificateDoc> mapCertificatesToCertificateDocs(List<Certificate> certificates) {
        return certificates.stream()
                .map(c -> new CertificateDoc(c.getId(), c.getTitle()))
                .toList();
    }
}

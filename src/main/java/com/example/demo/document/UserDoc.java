package com.example.demo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "users")
public class UserDoc {
    @Id
    private String id;

    private Long userId;
    private String name;
    private String email;
    private List<CertificateDoc> certificates;
}

package ru.otus.library.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDate;

@Document(collection = "author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorMongo {
    @Id
    private String id;
    private String name;
    private LocalDate dateOfBirth;
}

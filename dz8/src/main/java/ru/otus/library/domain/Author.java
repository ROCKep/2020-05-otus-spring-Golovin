package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    private String id;

    private String name;

    private LocalDate dateOfBirth;

    public Author(String name, LocalDate dateOfBirth) {
        this(null, name, dateOfBirth);
    }
}

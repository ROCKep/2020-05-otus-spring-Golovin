package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private String id;

    private String name;

    private Integer releaseYear;

    @DBRef
    private List<Genre> genres;

    @DBRef
    private Author author;

    public Book(String name, Integer releaseYear, List<Genre> genres, Author author) {
        this(null, name, releaseYear, genres, author);
    }

    public Book(String id, String name, Integer releaseYear) {
        this(id, name, releaseYear, null, null);
    }

    public Book(String name, Integer releaseYear) {
        this(null, name, releaseYear);
    }
}

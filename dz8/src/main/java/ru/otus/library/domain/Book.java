package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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

    private List<Genre> genres;

    @DBRef
    private Author author;

    public Book(String id, @NonNull String name, Integer releaseYear) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public Book(@NonNull String name, Integer releaseYear) {
        this(null, name, releaseYear);
    }

    public Book(@NonNull String name, Integer releaseYear, @NonNull List<Genre> genres, @NonNull Author author) {
        this(null, name, releaseYear, genres, author);
    }
}

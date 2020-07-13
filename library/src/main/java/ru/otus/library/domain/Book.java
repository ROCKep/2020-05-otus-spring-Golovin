package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Book {
    private Long id;
    private final String name;
    private final Integer releaseYear;
    private List<Genre> genres;
    private Author author;

    public Book(long id, @NonNull String name, Integer releaseYear) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public Book(@NonNull String name, Integer releaseYear, @NonNull List<Genre> genres, @NonNull Author author) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.genres = genres;
        this.author = author;
    }

    public String shortString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%d. %s", id, name));
        if (releaseYear != null) {
            builder.append(String.format(" (%d)", releaseYear));
        }
        return builder.toString();
    }

    public String longString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s%n", shortString()));
        builder.append(String.format("\tAuthor: %s%n", author.getName()));
        if (!genres.isEmpty()) {
            builder.append(String.format("\tGenres: %s", genres.stream().map(Genre::getName).collect(Collectors.joining(", "))));
        }
        return builder.toString();
    }
}

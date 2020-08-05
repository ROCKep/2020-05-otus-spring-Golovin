package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "genres_books", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @ManyToOne(fetch = FetchType.LAZY)
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

package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    public Book(String name, Integer releaseYear, List<Genre> genres, Author author) {
        this(null, name, releaseYear, genres, author);
    }

    public Book(Long id, String name, Integer releaseYear) {
        this(id, name, releaseYear, null, null);
    }

    public Book(String name, Integer releaseYear) {
        this(null, name, releaseYear);
    }
}

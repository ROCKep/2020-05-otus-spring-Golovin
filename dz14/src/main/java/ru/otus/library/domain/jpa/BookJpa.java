package ru.otus.library.domain.jpa;

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
public class BookJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "genres_books", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreJpa> genres;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthorJpa author;
}

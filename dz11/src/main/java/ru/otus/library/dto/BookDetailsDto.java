package ru.otus.library.dto;

import lombok.*;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDto {
    private String id;
    private String name;
    private String author;
    private List<String> genres;
    private Integer releaseYear;

    public static BookDetailsDto from(@NonNull Book book) {
        return new BookDetailsDto(
                book.getId(),
                book.getName(),
                book.getAuthor().getName(),
                book.getGenres().stream()
                        .map(Genre::getName)
                        .collect(Collectors.toList()),
                book.getReleaseYear());
    }
}

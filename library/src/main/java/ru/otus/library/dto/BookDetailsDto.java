package ru.otus.library.dto;

import lombok.*;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDto {
    private Long id;
    private String name;
    private String authorName;
    private String genreNames;
    private Integer releaseYear;

    public static BookDetailsDto from(@NonNull Book book) {
        return new BookDetailsDto(
                book.getId(),
                book.getName(),
                book.getAuthor().getName(),
                book.getGenres().stream()
                        .map(Genre::getName)
                        .collect(Collectors.joining(", ")),
                book.getReleaseYear());
    }
}

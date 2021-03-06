package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookDto {
    private String id;
    private String name;
    private String author;
    private Integer releaseYear;
}

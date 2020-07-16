package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Author {
    private Long id;
    private final String name;
    private final LocalDate dateOfBirth;
}

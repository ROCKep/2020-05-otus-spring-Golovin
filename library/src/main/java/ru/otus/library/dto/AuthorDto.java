package ru.otus.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorDto {
    private long id;
    private String name;
}

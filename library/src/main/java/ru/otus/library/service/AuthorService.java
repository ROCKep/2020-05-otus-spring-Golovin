package ru.otus.library.service;

import ru.otus.library.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> listAuthors();
}

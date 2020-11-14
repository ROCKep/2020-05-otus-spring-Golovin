package ru.otus.library.repository;

import ru.otus.library.domain.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getByNames(List<String> names);
}

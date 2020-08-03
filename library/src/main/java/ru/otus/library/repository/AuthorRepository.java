package ru.otus.library.repository;

import ru.otus.library.domain.Author;

public interface AuthorRepository {

    Author getByName(String name);

}

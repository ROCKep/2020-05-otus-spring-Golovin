package ru.otus.library.dao;

import ru.otus.library.domain.Author;

public interface AuthorDAO {

    Author getByName(String name);

}

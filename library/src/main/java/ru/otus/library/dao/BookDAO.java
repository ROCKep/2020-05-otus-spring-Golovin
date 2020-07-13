package ru.otus.library.dao;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookDAO {
    List<Book> getAll();
    Book getById(long id);
    Book add(Book book);

    boolean delete(long id);
}

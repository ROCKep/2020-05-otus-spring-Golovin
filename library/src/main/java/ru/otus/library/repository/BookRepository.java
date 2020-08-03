package ru.otus.library.repository;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAll();
    Book getById(long id);
    Book getByIdNoDetails(long id);
    Book add(Book book);
    boolean delete(long id);
}

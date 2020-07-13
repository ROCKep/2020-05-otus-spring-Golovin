package ru.otus.library.service;

public interface BookService {

    void listAllBooks();

    void getBookDetails(long id);

    void addNewBook();

    void deleteBook(long id);
}

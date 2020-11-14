package ru.otus.library.service;

public interface BookService {

    void listAllBooks();

    void getBookDetails(String id);

    void addNewBook();

    void deleteBook(String id);
}

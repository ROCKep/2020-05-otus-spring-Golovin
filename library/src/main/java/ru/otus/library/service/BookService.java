package ru.otus.library.service;

import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDetailsDto;

import java.util.List;

public interface BookService {

    List<Book> listAllBooks();

    BookDetailsDto getBookDetails(String id);

    String addNewBook(BookDetailsDto bookDetails);

    void editBook(BookDetailsDto bookDetails);

    void deleteBook(String id);
}

package ru.otus.library.service;

import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookDetailsDto;
import ru.otus.library.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> listAllBooks();

    Book getBook(long id);

    BookDetailsDto getBookDetails(long id);

    BookDetailsDto addNewBook(BookDetailsDto bookDetails);

    void editBook(BookDetailsDto bookDetails);

    void deleteBook(long id);
}

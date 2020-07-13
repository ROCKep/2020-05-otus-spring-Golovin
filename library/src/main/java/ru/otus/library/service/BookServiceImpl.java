package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final IOService ioService;
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final GenreDAO genreDAO;

    @Override
    public void listAllBooks() {
        ioService.outputLine("Listing all books:");
        List<Book> books = bookDAO.getAll();
        books.forEach(book -> {
            ioService.outputLine(book.shortString());
        });
    }

    @Override
    public void getBookDetails(long id) {
        ioService.outputLine("Book details:");
        Book book = bookDAO.getById(id);
        ioService.outputLine(book.longString());
    }

    @Override
    public void addNewBook() {
        ioService.output("input name: ");
        String name = ioService.inputLine();
        ioService.output("input release year: ");
        int releaseYear = ioService.inputInteger();
        ioService.output("input author name: ");
        String authorName = ioService.inputLine();
        ioService.output("input genre names: ");
        String genreNames = ioService.inputLine();
        Author author = authorDAO.getByName(authorName);
        List<Genre> genres = genreDAO.getByNames(Arrays.asList(genreNames.split(", ")));
        Book book = new Book(name, releaseYear, genres, author);
        book = bookDAO.add(book);
        ioService.outputLine("inserted book");
        ioService.outputLine(book.longString());
    }

    @Override
    public void deleteBook(long id) {
        boolean deleted = bookDAO.delete(id);
        if (deleted) {
            ioService.outputLine(String.format("deleted book with id %s", id));
        }
    }
}

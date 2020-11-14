package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final IOService ioService;
    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    @Override
    @Transactional(readOnly = true)
    public void listAllBooks() {
        ioService.outputLine("Listing all books:");
        List<Book> books = bookRepo.findAll();
        books.forEach(book ->
                ioService.outputLine(getBookShortString(book)));
    }

    @Override
    @Transactional(readOnly = true)
    public void getBookDetails(String id) {
        ioService.outputLine("Book details:");
        Book book = bookRepo.getById(id);
        ioService.outputLine(getBookLongString(book));
    }

    @Override
    @Transactional
    public void addNewBook() {
        ioService.output("input name: ");
        String name = ioService.inputLine();
        ioService.output("input release year: ");
        int releaseYear = ioService.inputInteger();
        ioService.output("input author name: ");
        String authorName = ioService.inputLine();
        ioService.output("input genre names: ");
        String genreNames = ioService.inputLine();
        Author author = authorRepo.findByName(authorName);
        List<Genre> genres = Arrays.stream(genreNames.split(", "))
                .map(Genre::new)
                .collect(Collectors.toList());
        Book book = new Book(name, releaseYear, genres, author);
        book = bookRepo.save(book);
        ioService.outputLine("inserted book");
        ioService.outputLine(getBookLongString(book));
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        bookRepo.deleteById(id);
        ioService.outputLine(String.format("deleted book with id %s", id));
    }

    String getBookShortString(Book book) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s. %s", book.getId(), book.getName()));
        if (book.getReleaseYear() != null) {
            builder.append(String.format(" (%d)", book.getReleaseYear()));
        }
        return builder.toString();
    }

    String getBookLongString(Book book) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s%n", getBookShortString(book)));
        builder.append(String.format("\tAuthor: %s%n", book.getAuthor().getName()));
        if (!book.getGenres().isEmpty()) {
            builder.append(String.format("\tGenres: %s",
                    book.getGenres().stream()
                            .map(Genre::getName).collect(Collectors.joining(", "))));
        }
        return builder.toString();
    }
}

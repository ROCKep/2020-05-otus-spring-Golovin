package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookDetailsDto;
import ru.otus.library.dto.BookDto;
import ru.otus.library.exception.NoDataFoundException;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @GetMapping("/api/books")
    public Flux<BookDto> listAllBooks() {
        return bookRepository.findAll()
                .map(book ->
                        new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getReleaseYear()));
    }

    @GetMapping("/api/book/{id}")
    public Mono<BookDetailsDto> getBookDetails(@PathVariable String id) {
        return bookRepository.findById(id)
                .map(BookDetailsDto::from)
                .switchIfEmpty(Mono.error(() -> new NoDataFoundException(String.format("Книга с id '%s' не найдена", id))));
    }

    @PostMapping("/api/book")
    public Mono<BookDetailsDto> addNewBook(@RequestBody BookDetailsDto bookDetails) {

        Mono<Author> author = authorRepository.findByName(bookDetails.getAuthor())
                .switchIfEmpty(Mono.error(() -> new NoDataFoundException(String.format("Автора с именем '%s' не существует", bookDetails.getAuthor()))));
        Flux<Genre> genres = genreRepository.findByNameIn(bookDetails.getGenres());

        return Mono.zip(author, genres.collectList())
                .flatMap(zipped -> bookRepository.save(new Book(bookDetails.getName(), bookDetails.getReleaseYear(), zipped.getT2(), zipped.getT1())))
                .map(BookDetailsDto::from);
    }

    @PutMapping("/api/book/{id}")
    public Mono<BookDetailsDto> editBook(@PathVariable String id, @RequestBody BookDetailsDto bookDetails) {

        Mono<Book> bookMono = bookRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new NoDataFoundException(String.format("Книги с id '%s' не существует", bookDetails.getId()))));
        Mono<Author> authorMono = authorRepository.findByName(bookDetails.getAuthor())
                .switchIfEmpty(Mono.error(() -> new NoDataFoundException(String.format("Автора с именем '%s' не существует", bookDetails.getAuthor()))));
        Flux<Genre> genresMono = genreRepository.findByNameIn(bookDetails.getGenres());

        return Mono.zip(bookMono, authorMono, genresMono.collectList())
                .flatMap(zipped -> {
                    Book book = zipped.getT1();
                    book.setName(bookDetails.getName());
                    book.setReleaseYear(bookDetails.getReleaseYear());
                    book.setAuthor(zipped.getT2());
                    book.setGenres(zipped.getT3());
                    return bookRepository.save(book);
                })
                .map(BookDetailsDto::from);
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<Void> deleteBook(@PathVariable String id) {
        Mono<Book> bookMono = bookRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new NoDataFoundException(String.format("Книга с id '%s' не найдена", id))));
        return bookMono.flatMap(book -> bookRepository.deleteById(book.getId()));
    }
}

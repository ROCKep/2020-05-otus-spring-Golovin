package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.dto.BookDetailsDto;
import ru.otus.library.exception.BadRequestException;
import ru.otus.library.exception.NoDataFoundException;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Book> listAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BookDetailsDto getBookDetails(String id) {
        Book book = bookRepo.findById(id).orElseThrow(() ->
                new NoDataFoundException(String.format("Книга с id '%s' не найдена", id)));
        return BookDetailsDto.from(book);
    }

    @Override
    @Transactional
    public String addNewBook(BookDetailsDto bookDetails) {
        Author author = authorRepo.findByName(bookDetails.getAuthorName()).orElseThrow(() ->
                new BadRequestException(String.format("Автора с именем '%s' не существует", bookDetails.getAuthorName())));
        List<Genre> genres = generateGenres(bookDetails.getGenreNames());
        Book book = new Book(bookDetails.getName(), bookDetails.getReleaseYear(), genres, author);
        book = bookRepo.save(book);
        return book.getId();
    }

    @Override
    public void editBook(BookDetailsDto bookDetails) {
        Author author = authorRepo.findByName(bookDetails.getAuthorName()).orElseThrow(() ->
                new BadRequestException(String.format("Автора с именем '%s' не существует", bookDetails.getAuthorName())));
        List<Genre> genres = generateGenres(bookDetails.getGenreNames());
        Book book = bookRepo.findById(bookDetails.getId()).orElseThrow(() ->
                new BadRequestException(String.format("Книги с id '%s' не существует", bookDetails.getId())));
        book.setName(bookDetails.getName());
        book.setReleaseYear(bookDetails.getReleaseYear());
        book.setAuthor(author);
        book.setGenres(genres);
        bookRepo.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        bookRepo.findById(id).orElseThrow(() ->
                new NoDataFoundException(String.format("Книга с id '%s' не найдена", id)));
        bookRepo.deleteById(id);
    }

    private List<Genre> generateGenres(String genreNames) {
        return Arrays.stream(genreNames.split(", "))
                .map(Genre::new)
                .collect(Collectors.toList());
    }
}

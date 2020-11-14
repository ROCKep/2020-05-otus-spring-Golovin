package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepo;
    @Mock
    private AuthorRepository authorRepo;
    @Mock
    private GenreRepository genreRepo;

    @Mock
    private IOService ioService;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    void testListAllBooks() {
        List<Book> books = Arrays.asList(
                new Book(-1L, "test book 1", 1945),
                new Book(-2L, "test book 2", 1950));
        doReturn(books).when(bookRepo).findAll();
        service.listAllBooks();
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService, times(3)).outputLine(captor.capture());
        List<String> output = captor.getAllValues();
        assertEquals(output.get(0), "Listing all books:");
        assertEquals(output.get(1), service.getBookShortString(books.get(0)));
        assertEquals(output.get(2), service.getBookShortString(books.get(1)));
    }

    @Test
    void testGetBookDetails() {
        List<Genre> genres = Collections.singletonList(new Genre(-1L, "test genre"));
        Author author = new Author(-1L, "test author", null);
        Book book = new Book(-1L, "test book", 1945, genres, author);
        doReturn(book).when(bookRepo).getByIdWithDetails(anyLong());
        service.getBookDetails(-1L);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService, times(2)).outputLine(captor.capture());
        List<String> output = captor.getAllValues();
        assertEquals(output.get(0), "Book details:");
        assertEquals(output.get(1), service.getBookLongString(book));
    }

    @Test
    void testAddNewBook() {
        List<Genre> genres = Collections.singletonList(new Genre(-1L, "test genre"));
        Author author = new Author(-1L, "test author", null);
        Book book = new Book(-1L, "test book", 1945, genres, author);

        doReturn(book.getName(), author.getName(), genres.get(0).getName())
                .when(ioService).inputLine();
        doReturn(book.getReleaseYear())
                .when(ioService).inputInteger();
        doReturn(author)
                .when(authorRepo).getByName(anyString());
        doReturn(genres)
                .when(genreRepo).getByNameIn(anyList());

        doReturn(book).when(bookRepo).save(any(Book.class));
        service.addNewBook();
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService, times(2)).outputLine(captor.capture());
        List<String> output = captor.getAllValues();
        assertEquals("inserted book", output.get(0));
        assertEquals(service.getBookLongString(book), output.get(1));
    }

    @Test
    void testDeleteBook() {
        doReturn(1).when(bookRepo).removeById(anyLong());
        service.deleteBook(-1L);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService).outputLine(captor.capture());
        assertEquals("deleted book with id -1", captor.getValue());
    }

    @Test
    void testGetBookShortString() {
        String expected = "1. test book (1965)";
        Book book = new Book(1, "test book", 1965);
        assertEquals(expected, service.getBookShortString(book));
    }

    @Test
    void testGetBookLongString() {
        String expected = String.format("1. test book (1965)%n" +
                "\tAuthor: test author%n" +
                "\tGenres: test genre 1, test genre 2");
        Author author = new Author(1L, "test author", null);
        List<Genre> genres = Arrays.asList(
                new Genre(1L, "test genre 1"),
                new Genre(2L, "test genre 2")
        );
        Book book = new Book(1L, "test book", 1965, genres, author);
        assertEquals(expected, service.getBookLongString(book));
    }
}
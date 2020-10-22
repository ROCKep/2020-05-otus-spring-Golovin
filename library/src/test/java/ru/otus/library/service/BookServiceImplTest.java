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
import ru.otus.library.dto.BookDetailsDto;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepo;
    @Mock
    private AuthorRepository authorRepo;
    @Mock
    private GenreRepository genreRepo;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    void testListAllBooks() {
        List<Book> expectedBooks = Arrays.asList(
                new Book(-1L, "test book 1", 1945),
                new Book(-2L, "test book 2", 1950));
        doReturn(expectedBooks).when(bookRepo).findAll();
        List<Book> actualBooks = service.listAllBooks();
        assertIterableEquals(expectedBooks, actualBooks);
    }

    @Test
    void testGetBook() {
        Book book = new Book(-1L, "test book", 1945, null, null);
        doReturn(Optional.of(book)).when(bookRepo).findById(anyLong());
        Book actualBook = service.getBook(-1L);
        assertEquals(book, actualBook);
    }

    @Test
    void testGetBookDetails() {
        List<Genre> genres = List.of(
                new Genre(-1L, "test genre 1"),
                new Genre(-2L, "test genre 2"));
        Author author = new Author(-1L, "test author", null);
        Book book = new Book(-1L, "test book", 1945, genres, author);
        doReturn(Optional.of(book)).when(bookRepo).findByIdWithDetails(anyLong());
        BookDetailsDto expectedBookDetails = new BookDetailsDto(-1L, "test book", "test author",
                "test genre 1, test genre 2", 1945);
        BookDetailsDto actualBookDetails = service.getBookDetails(-1L);
        assertEquals(expectedBookDetails, actualBookDetails);
    }

    @Test
    void testAddNewBook() {
        List<Genre> genres = List.of(
                new Genre(-1L, "test genre 1"),
                new Genre(-2L, "test genre 2"));
        Author author = new Author(-1L, "test author", null);
        Book book = new Book(-1L, "test book", 1945, genres, author);

        doReturn(Optional.of(author))
                .when(authorRepo).findByName(anyString());

        doReturn(genres).when(genreRepo).findByNameIn(anyList());

        doReturn(book).when(bookRepo).save(any(Book.class));

        BookDetailsDto bookDetails = new BookDetailsDto(null, "test book", "test author",
                "test genre 1, test genre 2", 1945);
        long id = service.addNewBook(bookDetails);
        assertEquals(-1L, id);
    }

    @Test
    void testEditBook() {
        List<Genre> genres = List.of(
                new Genre(-1L, "test genre 1"),
                new Genre(-2L, "test genre 2"));
        Author author = new Author(-1L, "test author", null);
        Book book = new Book(-1L, "test book", 1945, genres, author);
        Book bookEdited = new Book(-1L, "test book edited", 1946, genres, author);

        doReturn(Optional.of(author))
                .when(authorRepo).findByName(anyString());

        doReturn(genres).when(genreRepo).findByNameIn(anyList());

        doReturn(Optional.of(book)).when(bookRepo).findById(anyLong());

        BookDetailsDto bookDetails = new BookDetailsDto(-1L, "test book edited", "test author",
                "test genre 1, test genre 2", 1946);
        service.editBook(bookDetails);
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepo).save(bookCaptor.capture());
        assertEquals(bookEdited, bookCaptor.getValue());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book(-1L, "test book", 1945);
        doReturn(Optional.of(book)).when(bookRepo).findById(anyLong());
        service.deleteBook(-1L);
        verify(bookRepo).deleteById(-1L);
    }

}
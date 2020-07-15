package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BookDAOJdbc.class)
class BookDAOJdbcTest {

    @Autowired
    private BookDAOJdbc bookDAO;

    @Test
    void testGetAll() {
        List<Book> books = bookDAO.getAll();
        assertThat(books).hasSize(2);
        assertThat(books.get(0))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "test book 1")
                .hasFieldOrPropertyWithValue("releaseYear", 1995);
    }

    @Test
    void testGetById() {
        Book book = bookDAO.getById(2L);
        assertThat(book)
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("name", "test book 2")
                .hasFieldOrPropertyWithValue("releaseYear", null);
        assertThat(book.getAuthor())
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "test author")
                .hasFieldOrPropertyWithValue("dateOfBirth", LocalDate.of(1991, 12, 31));
        assertThat(book.getGenres()).hasSize(1);
        assertThat(book.getGenres().get(0))
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("name", "test genre 2");
    }

    @Test
    void testAdd() {
        List<Genre> genres = Collections.singletonList(new Genre(1L, null));
        Author author = new Author(1L, null, null);
        Book bookToAdd = new Book("new test book", 1965, genres, author);
        bookToAdd = bookDAO.add(bookToAdd);
        Book addedBook = bookDAO.getById(bookToAdd.getId());
        assertThat(addedBook).isEqualToIgnoringGivenFields(bookToAdd, "author", "genres");
        assertThat(addedBook.getAuthor()).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(addedBook.getGenres())
                .usingElementComparatorIgnoringFields("name")
                .containsExactlyInAnyOrderElementsOf(bookToAdd.getGenres());
    }

    @Test
    void delete() {
    }
}
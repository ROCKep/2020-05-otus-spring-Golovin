package ru.otus.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepo;
    @Autowired
    private TestEntityManager em;

    @Test
    void testGetAll() {
        List<Book> books = bookRepo.getAll();
        assertThat(books).hasSize(2);
        assertThat(books.get(0))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "test book 1")
                .hasFieldOrPropertyWithValue("releaseYear", 1995);
    }

    @Test
    void testGetById() {
        Book book = bookRepo.getById(2L);
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
    public void testGetByIdNoDetails() {
        Book book = bookRepo.getByIdNoDetails(2L);
        assertThat(book)
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("name", "test book 2")
                .hasFieldOrPropertyWithValue("releaseYear", null);
    }

    @Test
    void testAdd() {
        List<Genre> genres = Collections.singletonList(new Genre(1L, null));
        Author author = new Author(1L, null, null);
        Book bookToAdd = new Book("new test book", 1965, genres, author);
        bookToAdd = bookRepo.add(bookToAdd);
        em.detach(bookToAdd);
        Book addedBook = bookRepo.getById(bookToAdd.getId());
        assertThat(addedBook).isEqualToIgnoringGivenFields(bookToAdd, "author", "genres");
        assertThat(addedBook.getAuthor()).hasFieldOrPropertyWithValue("id", 1L);
        assertThat(addedBook.getGenres())
                .usingElementComparatorIgnoringFields("name")
                .containsExactlyInAnyOrderElementsOf(bookToAdd.getGenres());
    }

    @Test
    void testDelete() {
        bookRepo.delete(1L);
        em.flush();
        Book book = em.find(Book.class, 1L);
        assertNull(book);
    }
}
package ru.otus.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.library.domain.Book;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "ru.otus.library.repository")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepo;

    @Test
    void testFindByIdWithDetails() {
        Book book = bookRepo.findByIdWithDetails(2L).get();
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
}
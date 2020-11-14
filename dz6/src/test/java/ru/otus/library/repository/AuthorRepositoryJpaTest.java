package ru.otus.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AuthorRepositoryJpa.class})
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa authorRepo;

    @Test
    void testGetByName() {
        String authorName = "test author";
        Author author = authorRepo.getByName(authorName);
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", authorName)
                .hasFieldOrPropertyWithValue("dateOfBirth", LocalDate.of(1991, 12, 31));
    }
}
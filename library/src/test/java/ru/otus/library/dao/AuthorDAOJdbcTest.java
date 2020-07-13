package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({AuthorDAOJdbc.class})
class AuthorDAOJdbcTest {

    @Autowired
    private AuthorDAOJdbc authorDAO;

    @Test
    void testGetByName() {
        String authorName = "test author";
        Author author = authorDAO.getByName(authorName);
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", authorName)
                .hasFieldOrPropertyWithValue("dateOfBirth", LocalDate.of(1991, 12, 31));
    }
}
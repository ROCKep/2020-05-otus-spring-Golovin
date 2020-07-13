package ru.otus.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDAOJdbc.class)
class GenreDAOJdbcTest {

    @Autowired
    GenreDAOJdbc genreDAO;

    @Test
    void getByNames() {
        List<String> names = Arrays.asList("test genre 1", "test genre 2");
        List<Genre> genres = genreDAO.getByNames(names);
        assertThat(genres).hasSize(2);
        assertThat(genres.get(0))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", names.get(0));
    }
}
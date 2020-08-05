package ru.otus.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.library.domain.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan(basePackages = "ru.otus.library.repository")
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepo;

    @Test
    void getByNames() {
        List<String> names = Arrays.asList("test genre 1", "test genre 2");
        List<Genre> genres = genreRepo.getByNameIn(names);
        assertThat(genres).hasSize(2);
        assertThat(genres.get(0))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", names.get(0));
    }
}
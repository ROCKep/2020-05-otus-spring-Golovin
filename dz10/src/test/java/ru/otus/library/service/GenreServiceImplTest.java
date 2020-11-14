package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepo;

    @InjectMocks
    private GenreServiceImpl service;

    @Test
    void testListGenres() {
        List<Genre> genres = List.of(
                new Genre(-1L, "test genre 1"),
                new Genre(-2L, "test genre 2"));
        doReturn(genres).when(genreRepo).findAll();
        List<Genre> actualGenres = service.listGenres();
        assertIterableEquals(genres, actualGenres);
    }
}
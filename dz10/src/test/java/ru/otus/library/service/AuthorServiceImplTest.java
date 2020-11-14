package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.repository.AuthorRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepo;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    void testListAuthors() {
        List<Author> authors = List.of(
                new Author(-1L, "test author 1", LocalDate.of(1920, 5, 6)),
                new Author(-2L, "test author 2", null));
        doReturn(authors).when(authorRepo).findAll();
        List<AuthorDto> actualAuthors = service.listAuthors();
        assertThat(actualAuthors).hasSameSizeAs(authors);
        assertThat(actualAuthors.get(0)).hasFieldOrPropertyWithValue("id", -1L)
                .hasFieldOrPropertyWithValue("name", "test author 1");
        assertThat(actualAuthors.get(1)).hasFieldOrPropertyWithValue("id", -2L)
                .hasFieldOrPropertyWithValue("name", "test author 2");
    }
}
package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Test
    public void testListGenres() throws Exception {
        List<Genre> genres = Arrays.asList(
                new Genre(-1L, "test genre 1"),
                new Genre(-2L, "test genre 2"));
        doReturn(genres).when(genreService).listGenres();
        MvcResult result = mvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .contains(List.of(
                        "{\"id\":-1,\"name\":\"test genre 1\"}",
                        "{\"id\":-2,\"name\":\"test genre 2\"}"));
    }
}

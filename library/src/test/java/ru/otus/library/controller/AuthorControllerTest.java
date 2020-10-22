package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.service.AuthorService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthorController.class)
public class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    public void testListAuthors() throws Exception {
        List<AuthorDto> authors = Arrays.asList(
                new AuthorDto(-1L, "test author 1"),
                new AuthorDto(-2L, "test author 2"));
        doReturn(authors).when(authorService).listAuthors();
        MvcResult result = mvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .contains(List.of(
                        "{\"id\":-1,\"name\":\"test author 1\"}",
                        "{\"id\":-2,\"name\":\"test author 2\"}"));
    }
}

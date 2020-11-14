package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.library.dto.BookDetailsDto;
import ru.otus.library.dto.BookDto;
import ru.otus.library.service.BookService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testListAllBooks() throws Exception {
        List<BookDto> books = Arrays.asList(
                new BookDto(-1L, "test book 1", "test author 1", 1945),
                new BookDto(-2L, "test book 2", "test author 2", null));
        doReturn(books).when(bookService).listAllBooks();
        MvcResult result = mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .contains(List.of(
                        "{\"id\":-1,\"name\":\"test book 1\",\"author\":\"test author 1\",\"releaseYear\":1945}",
                        "{\"id\":-2,\"name\":\"test book 2\",\"author\":\"test author 2\",\"releaseYear\":null}"));
    }

    @Test
    public void testGetBookDetails() throws Exception {
        BookDetailsDto bookDetails = new BookDetailsDto(-1L, "test book", "test author",
                List.of("test genre 1, test genre 2"), 1945);
        doReturn(bookDetails).when(bookService).getBookDetails(anyLong());
        MvcResult result = mvc.perform(get("/api/book/-1"))
                .andExpect(status().isOk())
                .andReturn();
        verify(bookService).getBookDetails(-1L);
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("{\"id\":-1,\"name\":\"test book\",\"author\":\"test author\"," +
                        "\"genres\":[\"test genre 1, test genre 2\"],\"releaseYear\":1945}");
    }

    @Test
    public void testAddNewBook() throws Exception {
        String name = "test book";
        String author = "test author";
        List<String> genres = List.of("test genre 1, test genre 2");
        int releaseYear = 1945;
        BookDetailsDto newBookDetails = new BookDetailsDto(-1L, name, author,
                genres, releaseYear);
        doReturn(newBookDetails).when(bookService).addNewBook(any(BookDetailsDto.class));
        MvcResult result = mvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null,\"name\":\"test book\",\"author\":\"test author\"," +
                        "\"genres\":[\"test genre 1, test genre 2\"],\"releaseYear\":1945}"))
                .andExpect(status().isOk())
                .andReturn();
        verify(bookService).addNewBook(any(BookDetailsDto.class));
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("{\"id\":-1,\"name\":\"test book\",\"author\":\"test author\"," +
                        "\"genres\":[\"test genre 1, test genre 2\"],\"releaseYear\":1945}");
    }

    @Test
    public void testEditBook() throws Exception {
        mvc.perform(put("/api/book/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":-1,\"name\":\"test book\",\"author\":\"test author\"," +
                        "\"genres\":[\"test genre 1, test genre 2\"],\"releaseYear\":1945}"))
                .andExpect(status().isOk());
        verify(bookService).editBook(any(BookDetailsDto.class));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mvc.perform(delete("/api/book/-1"))
                .andExpect(status().isOk());
        verify(bookService).deleteBook(-1L);
    }

}

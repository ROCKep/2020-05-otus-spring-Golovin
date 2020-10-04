package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.library.domain.Book;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(LibraryController.class)
public class LibraryControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;

    @Test
    public void testListAllBooks() throws Exception {
        List<Book> books = Arrays.asList(
                new Book("-1", "test book 1", 1945),
                new Book("-2", "test book 2", 1950));
        doReturn(books).when(bookService).listAllBooks();
        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(contains("test book 1")))
                .andExpect(content().string(contains("test book 2")));
    }
}

package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.BookDetailsDto;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LibraryController.class)
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
                new Book(-1L, "test book 1", 1945),
                new Book(-2L, "test book 2", 1950));
        doReturn(books).when(bookService).listAllBooks();
        MvcResult result = mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .contains(List.of("test book 1", "test book 2"));
    }

    @Test
    public void testGetBookDetails() throws Exception {
        BookDetailsDto bookDetails = new BookDetailsDto(-1L, "test book", "test author",
                "test genre 1, test genre 2", 1945);
        doReturn(bookDetails).when(bookService).getBookDetails(anyLong());
        MvcResult result = mvc.perform(get("/books/-1"))
                .andExpect(status().isOk())
                .andReturn();
        verify(bookService).getBookDetails(-1L);
        assertThat(result.getResponse().getContentAsString())
                .contains(List.of("test book", "test author", "test genre 1, test genre 2", "1945"));
    }

    @Test
    public void testListBookComments() throws Exception {
        Book book = new Book(-1L, "test book", null);
        List<Comment> comments = List.of(
                new Comment(-1L, "test content 1", "test user 1", book),
                new Comment(-2L, "test content 2", "test user 2", book));
        doReturn(book).when(bookService).getBook(anyLong());
        doReturn(comments).when(commentService).listBookComments(anyLong());
        MvcResult result = mvc.perform(get("/books/-1/comments"))
                .andExpect(status().isOk())
                .andReturn();
        verify(bookService).getBook(-1L);
        verify(commentService).listBookComments(-1L);
        assertThat(result.getResponse().getContentAsString())
                .contains(List.of("test book", "test user 1", "test content 1", "test user 2", "test content 2"));
    }

    @Test
    public void testAddNewBookSubmit() throws Exception {
        BookDetailsDto bookDetails = new BookDetailsDto(null, "test book", "test author",
                "test genre 1, test genre 2", 1945);
        doReturn(-1L).when(bookService).addNewBook(any(BookDetailsDto.class));
        mvc.perform(post("/books/add").requestAttr("bookDetails", bookDetails))
                .andExpect(redirectedUrl("/books/-1"));
    }

    @Test
    public void testEditBookSubmit() throws Exception {
        BookDetailsDto bookDetails = new BookDetailsDto(-1L, "test book", "test author",
                "test genre 1, test genre 2", 1945);
        mvc.perform(post("/books/-1/edit").requestAttr("bookDetails", bookDetails))
                .andExpect(redirectedUrl("/books/-1"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mvc.perform(get("/books/-1/delete"))
                .andExpect(redirectedUrl("/books"));
        verify(bookService).deleteBook(-1L);
    }

    @Test
    public void testAddNewCommentSubmit() throws Exception {
        Comment comment = new Comment(-1L, "test content 1", "test user 1", null);
        mvc.perform(post("/books/-1/comments/add").requestAttr("comment", comment))
                .andExpect(redirectedUrl("/books/-1/comments"));
    }
}

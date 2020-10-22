package ru.otus.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.service.CommentService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CommentService commentService;

    @Test
    public void testListBookComments() throws Exception {
        List<CommentDto> comments = List.of(
                new CommentDto(-1L, "test content 1", "test user 1"),
                new CommentDto(-2L, "test content 2", "test user 2"));
        doReturn(comments).when(commentService).listBookComments(anyLong());
        MvcResult result = mvc.perform(get("/api/book/-1/comments"))
                .andExpect(status().isOk())
                .andReturn();
        verify(commentService).listBookComments(-1L);
        assertThat(result.getResponse().getContentAsString())
                .contains(List.of(
                        "{\"id\":-1,\"user\":\"test content 1\",\"content\":\"test user 1\"}",
                        "{\"id\":-2,\"user\":\"test content 2\",\"content\":\"test user 2\"}"));
    }

    @Test
    public void testAddNewComment() throws Exception {
        CommentDto newComment = new CommentDto(-1L, "test user", "test content");
        doReturn(newComment).when(commentService).addNewComment(anyLong(), any(CommentDto.class));
        MvcResult result = mvc.perform(post("/api/book/-1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null,\"user\":\"test user\",\"content\":\"test content\"}"))
                .andExpect(status().isOk())
                .andReturn();
        verify(commentService).addNewComment(anyLong(), any(CommentDto.class));
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("{\"id\":-1,\"user\":\"test user\",\"content\":\"test content\"}");
    }
}

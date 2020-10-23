package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private BookRepository bookRepo;
    @Mock
    private CommentRepository commentRepo;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void testListBookComments() {
        Book book = new Book(-1L, "test book", null);
        List<Comment> expectedComments = Arrays.asList(
                new Comment(-1L, "test content 1", "test user 1", book),
                new Comment(-2L, "test content 2", "test user 2", book));
        doReturn(true).when(bookRepo).existsById(anyLong());
        doReturn(expectedComments).when(commentRepo).findByBookId(anyLong());
        List<CommentDto> actualComments = commentService.listBookComments(-1L);
        assertThat(actualComments).hasSameSizeAs(expectedComments);
        assertThat(actualComments.get(0)).hasFieldOrPropertyWithValue("id", -1L)
                .hasFieldOrPropertyWithValue("content", "test content 1")
                .hasFieldOrPropertyWithValue("user", "test user 1");
        assertThat(actualComments.get(1)).hasFieldOrPropertyWithValue("id", -2L)
                .hasFieldOrPropertyWithValue("content", "test content 2")
                .hasFieldOrPropertyWithValue("user", "test user 2");
    }

    @Test
    public void testAddNewComment() {
        Book book = new Book(-1L, "test book", null);
        Comment comment = new Comment(-1L, "test content", "test user", book);
        doReturn(Optional.of(book)).when(bookRepo).findById(anyLong());
        doReturn(comment).when(commentRepo).save(any(Comment.class));
        CommentDto commentDto = new CommentDto(null, "test user", "test content");
        CommentDto newCommentDto = commentService.addNewComment(-1L, commentDto);
        assertThat(newCommentDto)
                .hasFieldOrPropertyWithValue("id", comment.getId())
                .hasFieldOrPropertyWithValue("user", commentDto.getUser())
                .hasFieldOrPropertyWithValue("content", commentDto.getContent());
    }
}

package ru.otus.library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {
    @Mock
    private IOService ioService;
    @Mock
    private BookRepository bookRepo;
    @Mock
    private CommentRepository commentRepo;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void testListBookComments() {
        Book book = new Book(1L, "test book", null);
        List<Comment> comments = Arrays.asList(
                new Comment(1L, "test content 1", "test user 1", book),
                new Comment(2L, "test content 2", "test user 2", book));
        doReturn(comments).when(commentRepo).getByBookId(anyLong());
        commentService.listBookComments(1L);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService, times(3)).outputLine(captor.capture());
        List<String> output = captor.getAllValues();
        assertEquals("comments for book \"test book\"", output.get(0));
        assertEquals(commentService.getCommentStringForShow(comments.get(0)), output.get(1));
        assertEquals(commentService.getCommentStringForShow(comments.get(1)), output.get(2));
    }

    @Test
    public void testAddComment() {
        Comment comment = new Comment(1L, "test content", "test user", null);
        doReturn(comment.getUser(), comment.getContent())
                .when(ioService).inputLine();
        Book book = new Book(1L, "test book", null);
        doReturn(book).when(bookRepo).getById(anyLong());
        doReturn(comment).when(commentRepo).save(any(Comment.class));
        commentService.addComment(1L);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService, times(2)).outputLine(captor.capture());
        List<String> output = captor.getAllValues();
        assertEquals("inserted comment", output.get(0));
        assertEquals(commentService.getCommentStringForShow(comment), output.get(1));
    }

    @Test
    public void testEditComment() {
        Comment oldComment = new Comment(1L, "test content", "test user", null);
        Comment newComment = new Comment(1L, "test content edited", "test user edited", null);
        doReturn(newComment.getUser(), newComment.getContent())
                .when(ioService).inputLine();
        doReturn(oldComment).when(commentRepo).getById(anyLong());
        doReturn(newComment).when(commentRepo).save(any(Comment.class));
        commentService.editComment(1L);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService, times(2)).outputLine(captor.capture());
        List<String> output = captor.getAllValues();
        assertEquals("updated comment", output.get(0));
        assertEquals(commentService.getCommentStringForShow(newComment), output.get(1));
    }

    @Test
    void testDeleteComment() {
        doReturn(1).when(commentRepo).removeById(anyLong());
        commentService.deleteComment(1L);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService).outputLine(captor.capture());
        assertEquals("deleted comment with id 1", captor.getValue());
    }

    @Test
    public void testGetCommentStringForShow() {
        Comment comment = new Comment("test content", "test user");
        comment.setId(1L);
        assertEquals(String.format("1. test user commented:%n\ttest content"), commentService.getCommentStringForShow(comment));
    }
}

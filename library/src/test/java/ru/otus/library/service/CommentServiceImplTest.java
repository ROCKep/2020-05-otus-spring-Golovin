//package ru.otus.library.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.otus.library.domain.Book;
//import ru.otus.library.domain.Comment;
//import ru.otus.library.repository.BookRepository;
//import ru.otus.library.repository.CommentRepository;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertIterableEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CommentServiceImplTest {
//
//    @Mock
//    private BookRepository bookRepo;
//    @Mock
//    private CommentRepository commentRepo;
//    @InjectMocks
//    private CommentServiceImpl commentService;
//
//    @Test
//    public void testListBookComments() {
//        Book book = new Book(-1L, "test book", null);
//        List<Comment> expectedComments = Arrays.asList(
//                new Comment(-1L, "test content 1", "test user 1", book),
//                new Comment(-2L, "test content 2", "test user 2", book));
//        doReturn(true).when(bookRepo).existsById(anyLong());
//        doReturn(expectedComments).when(commentRepo).findByBookId(anyLong());
//        List<Comment> actualComments = commentService.listBookComments(-1L);
//        assertIterableEquals(expectedComments, actualComments);
//    }
//
//    @Test
//    public void testAddComment() {
//        Comment comment = new Comment(null, "test content", "test user", null);
//        Book book = new Book(-1L, "test book", null);
//        doReturn(Optional.of(book)).when(bookRepo).findById(anyLong());
//        commentService.addComment(-1L, comment);
//        verify(commentRepo).save(any(Comment.class));
//    }
//}

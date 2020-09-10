package ru.otus.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ComponentScan(basePackages = {"ru.otus.library.repository", "ru.otus.library.event"})
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private CommentRepository commentRepo;

    @Test
    void testCascadeDelete() {
        Book book = bookRepo.findByName("test book 1");
        List<Comment> comments = commentRepo.findByBookId(book.getId());
        assertFalse(comments.isEmpty());
        bookRepo.deleteById(book.getId());
        comments = commentRepo.findByBookId(book.getId());
        assertTrue(comments.isEmpty());
    }
}
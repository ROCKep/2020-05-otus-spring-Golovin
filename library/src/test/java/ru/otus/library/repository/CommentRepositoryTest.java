package ru.otus.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@ComponentScan(basePackages = "ru.otus.library.repository")
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private TestEntityManager em;

    @Test
    void testGetByBookId() {
        List<Comment> comments = commentRepo.getByBookId(1L);
        assertThat(comments).hasSize(2);
        assertThat(comments.get(0))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("content", "test content 1")
                .hasFieldOrPropertyWithValue("user", "test user 1");
        Book book = comments.get(0).getBook();
        assertEquals(1L, book.getId());
    }

    @Test
    void testGetById() {
        Comment comment = commentRepo.getById(2L);
        assertThat(comment)
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("content", "test content 2")
                .hasFieldOrPropertyWithValue("user", "test user 2");
    }

    @Test
    void testSave() {
        Book book = em.find(Book.class, 2L);
        Comment commentToSave = new Comment(null, "test comment 4", "test user 4", book);
        commentToSave = commentRepo.save(commentToSave);
        em.detach(commentToSave);
        Comment addedComment = em.find(Comment.class, commentToSave.getId());
        assertThat(addedComment).isEqualToIgnoringGivenFields(commentToSave, "book");
        assertEquals(2L, addedComment.getBook().getId());
    }

    @Test
    void testUpdate() {
        Comment commentToUpdate = em.find(Comment.class, 1L);
        commentToUpdate.setContent("test content 1 edited");
        commentToUpdate.setUser("test user 1 edited");
        commentToUpdate = commentRepo.save(commentToUpdate);
        em.flush();
        em.detach(commentToUpdate);
        Comment updatedComment = em.find(Comment.class, commentToUpdate.getId());
        assertThat(updatedComment).isEqualToIgnoringGivenFields(commentToUpdate, "book");
    }

    @Test
    void testDelete() {
        commentRepo.removeById(1L);
        em.flush();
        Comment comment = em.find(Comment.class, 1L);
        assertNull(comment);
    }
}

package ru.otus.library.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
    Comment getById(String commentId);
    void removeByBookId(String bookId);
}

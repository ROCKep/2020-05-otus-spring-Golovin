package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
    void removeByBookId(String bookId);
}

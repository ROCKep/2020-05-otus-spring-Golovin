package ru.otus.library.repository;

import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getByBookId(long bookId);
    Comment save(Comment comment);
    Comment update(Comment comment);
    boolean delete(long id);

    Comment getById(long commentId);
}

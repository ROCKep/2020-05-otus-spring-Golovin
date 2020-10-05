package ru.otus.library.service;

import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listBookComments(long bookId);

    void addComment(long bookId, Comment comment);

}

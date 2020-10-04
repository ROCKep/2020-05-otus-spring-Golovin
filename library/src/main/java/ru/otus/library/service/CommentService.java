package ru.otus.library.service;

import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listBookComments(String bookId);

    void addComment(String bookId, Comment comment);

}

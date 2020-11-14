package ru.otus.library.service;

import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    void listBookComments(long bookId);

    void addComment(long bookId);

    @Transactional
    void editComment(long commentId);

    void deleteComment(long commentId);
}

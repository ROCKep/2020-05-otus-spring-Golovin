package ru.otus.library.service;

public interface CommentService {
    void listBookComments(String bookId);

    void addComment(String bookId);

    void editComment(String commentId);

    void deleteComment(String commentId);
}

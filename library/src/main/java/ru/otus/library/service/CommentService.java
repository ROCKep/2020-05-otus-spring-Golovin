package ru.otus.library.service;

import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> listBookComments(long bookId);

    CommentDto addNewComment(long bookId, CommentDto comment);

}

package ru.otus.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.service.CommentService;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/book/{bookId}/comments")
    public List<CommentDto> listBookComments(@PathVariable long bookId) {
        return commentService.listBookComments(bookId);
    }

    @PostMapping("/api/book/{bookId}/comment")
    public CommentDto addNewComment(@PathVariable long bookId, @RequestBody CommentDto comment) {
        return commentService.addNewComment(bookId, comment);
    }

}

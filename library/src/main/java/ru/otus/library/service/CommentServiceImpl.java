package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.exception.NoDataFoundException;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final BookRepository bookRepo;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> listBookComments(long bookId) {
        if (!bookRepo.existsById(bookId)) {
            throw new NoDataFoundException(String.format("Книга с id '%s' не найдена", bookId));
        }
        List<Comment> comments = commentRepo.findByBookId(bookId);
        return comments.stream()
                .map(comment ->
                        new CommentDto(comment.getId(), comment.getUser(), comment.getContent()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto addNewComment(long bookId, CommentDto commentDto) {
        Book book = bookRepo.findById(bookId).orElseThrow(() ->
                new NoDataFoundException(String.format("Книга с id '%s' не найдена", bookId)));

        Comment comment = new Comment(commentDto.getId(), commentDto.getContent(), commentDto.getUser(), book);
        comment = commentRepo.save(comment);
        return new CommentDto(comment.getId(), comment.getUser(), comment.getContent());
    }

}

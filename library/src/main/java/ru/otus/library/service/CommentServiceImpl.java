package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.exception.NoDataFoundException;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final BookRepository bookRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> listBookComments(long bookId) {
        if (!bookRepo.existsById(bookId)) {
            throw new NoDataFoundException(String.format("Книга с id '%s' не найдена", bookId));
        }
        return commentRepo.findByBookId(bookId);
    }

    @Override
    @Transactional
    public void addComment(long bookId, Comment comment) {
        Book book = bookRepo.findById(bookId).orElseThrow(() ->
                new NoDataFoundException(String.format("Книга с id '%s' не найдена", bookId)));
        comment.setBook(book);
        commentRepo.save(comment);
    }

}

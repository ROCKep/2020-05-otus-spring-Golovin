package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final IOService ioService;
    private final BookRepository bookRepo;

    @Override
    @Transactional(readOnly = true)
    public void listBookComments(String bookId) {
        List<Comment> comments = commentRepo.findByBookId(bookId);
        if (comments.size() > 0) {
            ioService.outputLine(String.format("comments for book \"%s\"", comments.get(0).getBook().getName()));
            comments.forEach(comment ->
                    ioService.outputLine(getCommentStringForShow(comment)));
        }
    }

    @Override
    @Transactional
    public void addComment(String bookId) {
        Book book = bookRepo.getById(bookId);
        ioService.output("input user: ");
        String user = ioService.inputLine();
        ioService.output("input content: ");
        String content = ioService.inputLine();
        var comment = new Comment(content, user);
        comment.setBook(book);
        comment = commentRepo.save(comment);
        ioService.outputLine("inserted comment");
        ioService.outputLine(getCommentStringForShow(comment));
    }

    @Override
    @Transactional
    public void editComment(String commentId) {
        Comment comment = commentRepo.getById(commentId);
        ioService.output("input user: ");
        String user = ioService.inputLine();
        ioService.output("input content: ");
        String content = ioService.inputLine();
        comment.setUser(user);
        comment.setContent(content);
        comment = commentRepo.save(comment);
        ioService.outputLine("updated comment");
        ioService.outputLine(getCommentStringForShow(comment));
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        commentRepo.deleteById(commentId);
        ioService.outputLine(String.format("deleted comment with id %s", commentId));
    }

    public String getCommentStringForShow(Comment comment) {
        return String.format("%s. %s commented:%n\t%s", comment.getId(), comment.getUser(), comment.getContent());
    }

}

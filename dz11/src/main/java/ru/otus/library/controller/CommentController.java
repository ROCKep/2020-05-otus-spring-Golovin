package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.CommentDto;
import ru.otus.library.exception.NoDataFoundException;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @GetMapping("/api/book/{bookId}/comments")
    public Flux<CommentDto> listBookComments(@PathVariable String bookId) {

        Mono<Book> bookMono = bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(() -> new NoDataFoundException(String.format("Книга с id '%s' не найдена", bookId))));

        return bookMono.flatMapMany(book -> commentRepository.findByBookId(book.getId()))
                .map(comment -> new CommentDto(comment.getId(), comment.getUser(), comment.getContent()));
    }

    @PostMapping("/api/book/{bookId}/comment")
    public Mono<CommentDto> addNewComment(@PathVariable String bookId, @RequestBody CommentDto commentDto) {
        Mono<Book> bookMono = bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(() -> new NoDataFoundException(String.format("Книга с id '%s' не найдена", bookId))));

        return bookMono
                .flatMap(book -> commentRepository.save(new Comment(commentDto.getId(), commentDto.getContent(), commentDto.getUser(), book)))
                .map(comment -> new CommentDto(comment.getId(), comment.getUser(), comment.getContent()));
    }

}

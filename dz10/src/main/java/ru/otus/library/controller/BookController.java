package ru.otus.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.dto.BookDetailsDto;
import ru.otus.library.dto.BookDto;
import ru.otus.library.service.BookService;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books")
    public List<BookDto> listAllBooks() {
        return bookService.listAllBooks();
    }

    @GetMapping("/api/book/{id}")
    public BookDetailsDto getBookDetails(@PathVariable long id) {
        return bookService.getBookDetails(id);
    }

    @PostMapping("/api/book")
    public BookDetailsDto addNewBook(@RequestBody BookDetailsDto bookDetails) {
        return bookService.addNewBook(bookDetails);
    }

    @PutMapping("/api/book/{id}")
    public void editBook(@PathVariable long id, @RequestBody BookDetailsDto bookDetails) {
        bookDetails.setId(id);
        bookService.editBook(bookDetails);
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
}

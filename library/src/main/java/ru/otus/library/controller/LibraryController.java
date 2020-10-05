package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Comment;
import ru.otus.library.dto.BookDetailsDto;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class LibraryController {
    private final BookService bookService;
    private final CommentService commentService;

    @GetMapping
    public String listAllBooks(Model model) {
        model.addAttribute("books", bookService.listAllBooks());
        return "listAllBooks";
    }

    @GetMapping("/{id}")
    public String getBookDetails(@PathVariable long id, Model model) {
        model.addAttribute("bookDetails", bookService.getBookDetails(id));
        return "getBookDetails";
    }

    @GetMapping("/{bookId}/comments")
    public String listBookComments(@PathVariable long bookId, Model model) {
        model.addAttribute("book", bookService.getBook(bookId));
        model.addAttribute("comments", commentService.listBookComments(bookId));
        return "listBookComments";
    }

    @GetMapping("/add")
    public String addNewBookForm(Model model) {
        model.addAttribute("bookDetails", new BookDetailsDto());
        return "addNewBook";
    }

    @PostMapping("/add")
    public String addNewBookSubmit(@ModelAttribute BookDetailsDto bookDetails) {
        long bookId = bookService.addNewBook(bookDetails);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/{bookId}/edit")
    public String editBookForm(@PathVariable long bookId, Model model) {
        model.addAttribute("bookDetails", bookService.getBookDetails(bookId));
        return "editBook";
    }

    @PostMapping("/{bookId}/edit")
    public String editBookSubmit(@PathVariable long bookId, @ModelAttribute BookDetailsDto bookDetails) {
        bookDetails.setId(bookId);
        bookService.editBook(bookDetails);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/{bookId}/delete")
    public String deleteBook(@PathVariable long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/books";
    }

    @GetMapping("/{bookId}/comments/add")
    public String addNewCommentForm(@PathVariable long bookId, Model model) {
        model.addAttribute("book", bookService.getBook(bookId));
        model.addAttribute("comment", new Comment());
        return "addNewComment";
    }

    @PostMapping("/{bookId}/comments/add")
    public String addNewCommentSubmit(@PathVariable long bookId, @ModelAttribute Comment comment) {
        commentService.addComment(bookId, comment);
        return String.format("redirect:/books/%s/comments", bookId);
    }
}

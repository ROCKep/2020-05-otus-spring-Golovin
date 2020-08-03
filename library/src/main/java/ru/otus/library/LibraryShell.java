package ru.otus.library;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.BookService;
import ru.otus.library.service.CommentService;

@ShellComponent
@RequiredArgsConstructor
public class LibraryShell {

    private final BookService bookService;
    private final CommentService commentService;

    @ShellMethod(value = "list all books", key = {"l", "list"})
    public void listAllBooks() {
        bookService.listAllBooks();
    }

    @ShellMethod(value = "get book details", key = {"b", "book"})
    public void getBookDetails(@ShellOption long id) {
        bookService.getBookDetails(id);
    }

    @ShellMethod(value = "add new book", key = {"a", "add"})
    public void addNewBook() {
        bookService.addNewBook();
    }

    @ShellMethod(value = "delete book", key = {"d", "delete"})
    public void deleteBook(@ShellOption long id) {
        bookService.deleteBook(id);
    }

    @ShellMethod(value = "list book comments", key = {"lc", "list-comments"})
    public void listBookComments(@ShellOption long bookId) {
        commentService.listBookComments(bookId);
    }

    @ShellMethod(value = "add comment", key = {"ac", "add-comment"})
    public void addComment(@ShellOption long commentId) {
        commentService.addComment(commentId);
    }

    @ShellMethod(value = "edit comment", key = {"ec", "edit-comment"})
    public void editComment(@ShellOption long commentId) {
        commentService.editComment(commentId);
    }

    @ShellMethod(value = "delete comment", key = {"dc", "delete-comment"})
    public void deleteComment(@ShellOption long commentId) {
        commentService.deleteComment(commentId);
    }
}

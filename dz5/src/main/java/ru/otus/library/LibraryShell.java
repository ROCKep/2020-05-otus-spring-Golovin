package ru.otus.library;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.BookService;

@ShellComponent
@RequiredArgsConstructor
public class LibraryShell {

    private final BookService bookService;

    @ShellMethod(value = "list all books", key = {"l", "list"})
    public void listAllBooks() {
        bookService.listAllBooks();
    }

    @ShellMethod(value = "list all books", key = {"b", "book"})
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
}

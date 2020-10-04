package ru.otus.library.testchangelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;

import java.time.LocalDate;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "golovin", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "golovin", runAlways = true)
    public void initAuthors(AuthorRepository repository){
        repository.saveAll(List.of(
                new Author("test author", LocalDate.of(1991, 12, 31))));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "golovin", runAlways = true)
    public void initBooks(BookRepository repository, AuthorRepository authorRepository){
        repository.saveAll(List.of(
                new Book("test bookDetails 1", 1995,
                        List.of(
                                new Genre("test genre 1")),
                        authorRepository.findByName("test author").get()),
                new Book("test bookDetails 2", null,
                        List.of(new Genre("test genre 2")),
                        authorRepository.findByName("test author").get())));
    }

    @ChangeSet(order = "003", id = "initComments", author = "golovin", runAlways = true)
    public void initComments(CommentRepository repository, BookRepository bookRepository){
        repository.saveAll(List.of(
                new Comment("test content 1", "test user 1", bookRepository.findByName("test bookDetails 1").get()),
                new Comment("test content 2", "test user 2", bookRepository.findByName("test bookDetails 1").get()),
                new Comment("test content 3", "test user 3", bookRepository.findByName("test bookDetails 2").get())
        ));
    }
}

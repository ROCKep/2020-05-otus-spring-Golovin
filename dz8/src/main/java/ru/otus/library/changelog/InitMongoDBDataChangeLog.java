package ru.otus.library.changelog;

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
                new Author("Джордж Оруэлл", LocalDate.of(1903,6,25)),
                new Author("Джоан Роулинг", LocalDate.of(1965, 7, 31)),
                new Author("Михаил Булгаков", null)));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "golovin", runAlways = true)
    public void initBooks(BookRepository repository, AuthorRepository authorRepository){
        repository.saveAll(List.of(
                new Book("1984", 1949,
                        List.of(
                                new Genre("Антиутопия"),
                                new Genre("Классика")),
                        authorRepository.findByName("Джордж Оруэлл")),
                new Book("Гарри Поттер и философский камень", null,
                        List.of(new Genre("Фэнтези")),
                        authorRepository.findByName("Джоан Роулинг")),
                new Book("Мастер и Маргарита", 1966,
                        List.of(new Genre("Классика")),
                        authorRepository.findByName("Михаил Булгаков"))));

    }

    @ChangeSet(order = "003", id = "initComments", author = "golovin", runAlways = true)
    public void initComments(CommentRepository repository, BookRepository bookRepository){
        repository.saveAll(List.of(
                new Comment("Крутая книга)", "turboVasyan123", bookRepository.findByName("Мастер и Маргарита")),
                new Comment("Не очень(", "coolDude2000", bookRepository.findByName("Мастер и Маргарита")),
                new Comment("Пойдет", "proGamerMLG360", bookRepository.findByName("1984"))
        ));
    }
}

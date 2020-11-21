package ru.otus.library.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.time.LocalDate;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "golovin", runAlways = true)
    public void dropDB(MongoDatabase mongoDatabase){
        mongoDatabase.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "golovin", runAlways = true)
    public void initAuthors(MongockTemplate mongockTemplate){
        mongockTemplate.save(new Author("-1", "Джордж Оруэлл", LocalDate.of(1903,6,25)));
        mongockTemplate.save(new Author("-2", "Джоан Роулинг", LocalDate.of(1965, 7, 31)));
        mongockTemplate.save(new Author("-3", "Михаил Булгаков", null));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "golovin", runAlways = true)
    public void initGenres(MongockTemplate mongockTemplate){
        mongockTemplate.save(new Genre("-1", "Антиутопия"));
        mongockTemplate.save(new Genre("-2", "Классика"));
        mongockTemplate.save(new Genre("-3", "Фэнтези"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "golovin", runAlways = true)
    public void initBooks(MongockTemplate mongockTemplate){

        Author a1 = mongockTemplate.findById("-1", Author.class);
        Author a2 = mongockTemplate.findById("-2", Author.class);
        Author a3 = mongockTemplate.findById("-3", Author.class);

        Genre g1 = mongockTemplate.findById("-1", Genre.class);
        Genre g2 = mongockTemplate.findById("-2", Genre.class);
        Genre g3 = mongockTemplate.findById("-3", Genre.class);

        mongockTemplate.save(new Book("-1", "1984", 1949, List.of(g1, g2), a1));
        mongockTemplate.save(new Book("-2", "Гарри Поттер и философский камень", null, List.of(g3), a2));
        mongockTemplate.save(new Book("-3", "Мастер и Маргарита", 1966, List.of(g2), a3));
    }

    @ChangeSet(order = "004", id = "initComments", author = "golovin", runAlways = true)
    public void initComments(MongockTemplate mongockTemplate){
        Book b1 = mongockTemplate.findById("-1", Book.class);
        Book b3 = mongockTemplate.findById("-3", Book.class);

        mongockTemplate.save(new Comment("-1", "Крутая книга)", "turboVasyan123", b3));
        mongockTemplate.save(new Comment("-2", "Не очень(", "coolDude2000", b3));
        mongockTemplate.save(new Comment("-3", "Пойдет", "proGamerMLG360", b1));
    }
}

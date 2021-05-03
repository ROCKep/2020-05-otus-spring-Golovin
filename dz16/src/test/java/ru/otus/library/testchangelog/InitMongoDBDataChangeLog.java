package ru.otus.library.testchangelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.time.LocalDate;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "golovin", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "golovin", runAlways = true)
    public void initAuthors(MongockTemplate mongockTemplate){
        mongockTemplate.save(new Author("-1", "test author", LocalDate.of(1991, 12, 31)));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "golovin", runAlways = true)
    public void initGenres(MongockTemplate mongockTemplate){
        mongockTemplate.save(new Genre("-1", "test genre"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "golovin", runAlways = true)
    public void initBooks(MongockTemplate mongockTemplate){
        Author author = mongockTemplate.findById("-1", Author.class);
        Genre genre = mongockTemplate.findById("-1", Genre.class);

        mongockTemplate.save(new Book("-1", "test book 1", 1926, List.of(genre), author));
    }
}

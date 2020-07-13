package ru.otus.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDAOJdbc implements BookDAO {

    private static final BookMapper BOOK_MAPPER = new BookMapper();

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public BookDAOJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select id, name, release_year from books", BOOK_MAPPER);
    }

    @Override
    public Book getById(long id) {
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbc.query(
            "select b.id, b.name, b.release_year, " +
                    "a.id author_id, a.name author_name, a.date_of_birth, " +
                    "g.id genre_id, g.name genre_name " +
                "from books b " +
                    "join authors a on b.author_id = a.id " +
                    "left join genres_books gb on b.id = gb.book_id " +
                    "left join genres g on gb.genre_id = g.id " +
                "where b.id = :id",
                params, rs -> {
                    rs.next();
                    Book book = new Book(rs.getLong("id"), rs.getString("name"), rs.getObject("release_year", Integer.class));
                    Author author = new Author(rs.getLong("author_id"), rs.getString("author_name"), rs.getObject("date_of_birth", LocalDate.class));
                    book.setAuthor(author);
                    List<Genre> genres = new ArrayList<>();
                    do {
                        Long genreId = rs.getObject("genre_id", Long.class);
                        if (genreId == null) {
                            break;
                        }
                        genres.add(new Genre(genreId, rs.getString("genre_name")));
                    } while (rs.next());
                    book.setGenres(genres);
                    return book;
                });
    }


    @Override
    public Book add(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", book.getName())
                .addValue("release_year", book.getReleaseYear())
                .addValue("author_id", book.getAuthor().getId());
        jdbc.update("insert into books (name, release_year, author_id) values (:name, :release_year, :author_id)", params, keyHolder);
        long id = keyHolder.getKey().longValue();
        book.setId(id);

        MapSqlParameterSource[] paramsArr = book.getGenres().stream()
                .map(genre -> new MapSqlParameterSource()
                        .addValue("genre_id", genre.getId())
                        .addValue("book_id", id))
                .toArray(MapSqlParameterSource[]::new);
        jdbc.batchUpdate("insert into genres_books (genre_id, book_id) values (:genre_id, :book_id)", paramsArr);

        return book;
    }

    @Override
    public boolean delete(long id) {
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbc.update("delete from genres_books where book_id = :id", params);
        return jdbc.update("delete from books where id = :id", params) > 0;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getLong("id"), rs.getString("name"),
                    rs.getObject("release_year", Integer.class));
        }
    }
}

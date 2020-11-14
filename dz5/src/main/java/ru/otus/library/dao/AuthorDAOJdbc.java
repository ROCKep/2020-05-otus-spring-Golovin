package ru.otus.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Repository
public class AuthorDAOJdbc implements AuthorDAO {

    private static final AuthorMapper AUTHOR_MAPPER = new AuthorMapper();

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public AuthorDAOJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author getByName(String name) {
        SqlParameterSource params = new MapSqlParameterSource("name", name);
        return jdbc.queryForObject("select id, name, date_of_birth from authors where name = :name",
                params, AUTHOR_MAPPER);
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("name"),
                    rs.getObject("date_of_birth", LocalDate.class));
        }
    }
}

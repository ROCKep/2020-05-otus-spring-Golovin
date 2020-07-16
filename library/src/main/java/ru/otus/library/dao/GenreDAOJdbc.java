package ru.otus.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreDAOJdbc implements GenreDAO {

    private static final GenreMapper GENRE_MAPPER = new GenreMapper();

    private final NamedParameterJdbcOperations jdbc;

    @Autowired
    public GenreDAOJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Genre> getByNames(List<String> names) {
        SqlParameterSource params = new MapSqlParameterSource("names", names);
        return jdbc.query("select id, name from genres where name in (:names)", params, GENRE_MAPPER);
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }
}

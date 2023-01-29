package ru.otus.lesson9.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.lesson9.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;


@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Genre> mapper = new GenreMapper();

    @Override
    public Genre getById(Long id) {
        final Map<String, Object> params = singletonMap("id", id);
        return jdbc.queryForObject("select * from genre where id = :id", params, mapper);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genre", mapper);
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }


}

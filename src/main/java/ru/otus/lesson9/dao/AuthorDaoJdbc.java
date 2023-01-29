package ru.otus.lesson9.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.lesson9.model.Author;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import static java.util.Collections.singletonMap;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao{

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Author> mapper = new AuthorMapper();


    @Override
    public Author getById(Long id) {
        final Map<String, Object> params = singletonMap("id", id);
        return jdbc.queryForObject("select id,name from author where id = :id", params, mapper);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id,name from author", mapper);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Author(id, name);
        }
    }



}

package ru.otus.lesson9.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.lesson9.model.Author;
import ru.otus.lesson9.model.Book;
import ru.otus.lesson9.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Collections.singletonMap;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Book> mapper = new BookDaoJdbc.BookMapper();

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from book", Integer.class);
    }

    @Override
    public Long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into book(name, author_id, genre_id) values (:name, :author_id, :genre_id)", params, kh);
        return Objects.requireNonNull(kh.getKey()).longValue();
    }

    @Override
    public Book getById(Long id) {
        final Map<String, Object> params = singletonMap("id", id);
        return jdbc.queryForObject("select b.id, b.name, b.author_id, b.genre_id, a.name as author_name, g.name as genre_name from book b " +
                "left join author a on b.author_id = a.id left join genre g on b.genre_id = g.id where b.id = :id", params, mapper);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id, b.name, b.author_id, b.genre_id, a.name as author_name, g.name as genre_name from book b " +
                "left join author a on b.author_id = a.id left join genre g on b.genre_id = g.id", mapper);
    }

    @Override
    public List<Book> getAllByAuthor(Author author) {
        final Map<String, Object> params = singletonMap("author_id", author.getId());
        return jdbc.query("select b.id, b.name, b.author_id, b.genre_id, a.name as author_name, g.name as genre_name from book b " +
                "left join author a on b.author_id = a.id left join genre g on b.genre_id = g.id where b.author_id = :author_id", params, mapper);
    }

    @Override
    public List<Book> getAllByGenre(Genre genre) {
        final Map<String, Object> params = singletonMap("genre_id", genre.getId());
        return jdbc.query("select b.id, b.name, b.author_id, b.genre_id, a.name as author_name, g.name as genre_name from book b " +
                "left join author a on b.author_id = a.id left join genre g on b.genre_id = g.id where b.genre_id = :genre_id", params, mapper);
    }

    @Override
    public List<Book> getAllByAuthorAndGenre(Author author, Genre genre) {
        final Map<String, Object> params = Map.of("author_id", author.getId(), "genre_id", genre.getId());
        return jdbc.query("select b.id, b.name, b.author_id, b.genre_id, a.name as author_name, g.name as genre_name from book b " +
                "left join author a on b.author_id = a.id left join genre g on b.genre_id = g.id " +
                "where b.author_id = :author_id and b.genre_id = :genre_id", params, mapper);
    }

    @Override
    public int updateById(Long id, String name, Long authorId, Long genreId) {
        final Map<String, Object> params = Map.of("id", id, "name", name, "author_id", authorId, "genre_id", genreId);
        return jdbc.update("update book set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id", params);
    }

    @Override
    public void deleteById(Long id) {
        final Map<String, Object> params = singletonMap("id", id);
        jdbc.update("delete from book where id = :id", params);
    }

    @RequiredArgsConstructor
    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Long id = rs.getLong("id");
            final String name = rs.getString("name");
            final Author author = new Author(rs.getLong("author_id"), rs.getString("author_name"));
            final Genre genre = new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
            return new Book(id, name, author, genre);
        }
    }




}

package ru.otus.lesson9.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.lesson9.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@JdbcTest
@Import(GenreDaoJdbc.class)
@DisplayName("Dao для работы с жанрами должно")
class GenreDaoJdbcTest {

    private static final long GENRE_ID = 3L;
    private static final String GENRE_NAME = "Comedy";
    private static final int EXPECTED_GENRES_COUNT = 4;

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    @DisplayName("возвращать ожидаемый жанр по его id")
    void getById() {
        Genre actualGenre = genreDaoJdbc.getById(GENRE_ID);
        assertThat(actualGenre)
                .hasFieldOrPropertyWithValue("id", GENRE_ID)
                .hasFieldOrPropertyWithValue("name", GENRE_NAME);
    }

    @Test
    @DisplayName("возвращать все жанры")
    void getAll() {
        List<Genre> allGenres = genreDaoJdbc.getAll();
        assertThat(allGenres).hasSize(EXPECTED_GENRES_COUNT);
    }
}
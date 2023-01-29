package ru.otus.lesson9.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.lesson9.model.Author;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;



@JdbcTest
@Import(AuthorDaoJdbc.class)
@DisplayName("Dao для работы с авторами должно")
class AuthorDaoJdbcTest {
    private static final long TEST_ID = 2L;
    private static final String TEST_NAME = "Klaus Rifbjerg";
    private static final int EXPECTED_AUTHORS_COUNT = 3;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    @DisplayName("возвращать ожидаемого автора по его id")
    void getById() {
        Author actualAuthor = authorDaoJdbc.getById(TEST_ID);
        assertThat(actualAuthor)
                .hasFieldOrPropertyWithValue("id", TEST_ID)
                .hasFieldOrPropertyWithValue("name", TEST_NAME);
    }

    @Test
    @DisplayName("возвращать всех авторов")
    void getAll() {
        List<Author> allAuthors = authorDaoJdbc.getAll();
        assertThat(allAuthors).hasSize(EXPECTED_AUTHORS_COUNT);
    }

}
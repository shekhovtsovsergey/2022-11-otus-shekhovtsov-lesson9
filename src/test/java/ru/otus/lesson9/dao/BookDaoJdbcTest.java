package ru.otus.lesson9.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.otus.lesson9.model.Author;
import ru.otus.lesson9.model.Book;
import ru.otus.lesson9.model.Genre;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;


@JdbcTest
@Import(BookDaoJdbc.class)
@DisplayName("Dao для работы с книгами должно")
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOKS_COUNT = 5;
    private static final long LONG_WALK_ID = 2L;
    private static final String LONG_WALK_NAME = "Ned ad trappen, ud på gaden (Danish Edition)";
    private static final long EXPECTED_NEW_ID = 6;
    private static final long PUSHKIN_ID = 3L;
    private static final long KING_ID = 2L;
    private static final long HORROR_ID = 4L;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    @DisplayName("возвращать ожидаемое количество книг в БД")
    void count() {
        int count = bookDaoJdbc.count();
        assertThat(count).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    @DisplayName("добавлять книгу в БД")
    void insert() {
        Book expectedBook = new Book(null,
                "Spring in Action",
                new Author(2L, "Klaus Rifbjerg"),
                new Genre(4L, "Comedy"));
        long id = bookDaoJdbc.insert(expectedBook);
        assertThat(id).isEqualTo(EXPECTED_NEW_ID);
        Book actualBook = bookDaoJdbc.getById(EXPECTED_NEW_ID);
        expectedBook.setId(EXPECTED_NEW_ID);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по ее id")
    void getById() {
        Book actualBook = bookDaoJdbc.getById(LONG_WALK_ID);
        assertThat(actualBook)
                .hasFieldOrPropertyWithValue("id", LONG_WALK_ID)
                .hasFieldOrPropertyWithValue("name", LONG_WALK_NAME);
    }

    @Test
    @DisplayName("возвращать все книги")
    void getAll() {
        List<Book> allBooks = bookDaoJdbc.getAll();
        assertThat(allBooks).hasSize(EXPECTED_BOOKS_COUNT);
    }

    @Test
    @DisplayName("возвращать все книги по автору")
    void getAllByAuthor() {
        List<Book> actualBooks = bookDaoJdbc.getAllByAuthor(new Author(PUSHKIN_ID, null));
        assertAll(() -> assertThat(actualBooks).hasSize(2),
                () -> assertThat(actualBooks.stream()).allMatch(book -> book.getAuthor().getId().equals(PUSHKIN_ID)));
    }

    @Test
    @DisplayName("возвращать все книги по жанру")
    void getAllByGenre() {
        List<Book> actualBooks = bookDaoJdbc.getAllByGenre(new Genre(HORROR_ID, null));
        assertAll(() -> assertThat(actualBooks).hasSize(2),
                () -> assertThat(actualBooks.stream()).allMatch(book -> book.getGenre().getId().equals(HORROR_ID)));
    }

    @Test
    @DisplayName("возвращать все книги по автору и жанру")
    void getAllByAuthorAndGenre() {
        List<Book> actualBooks = bookDaoJdbc.getAllByAuthorAndGenre(new Author(KING_ID, null), new Genre(HORROR_ID, null));
        assertAll(() -> assertThat(actualBooks).hasSize(2),
                () -> assertThat(actualBooks.stream()).allMatch(book -> book.getAuthor().getId().equals(KING_ID)
                        && book.getGenre().getId().equals(HORROR_ID)));
    }

    @Test
    @DisplayName("обновляет данные книги по id")
    void updateById() {
        bookDaoJdbc.updateById(LONG_WALK_ID, "testName", 3L, 2L);
        Book actualBook = bookDaoJdbc.getById(LONG_WALK_ID);
        assertAll(() -> assertThat(actualBook.getName()).isEqualTo("testName"),
                () -> assertThat(actualBook.getAuthor().getId()).isEqualTo(3L),
                () -> assertThat(actualBook.getGenre().getId()).isEqualTo(2L));
    }

    @Test
    @DisplayName("удаляет книгу по id")
    void deleteById() {
        bookDaoJdbc.deleteById(LONG_WALK_ID);
        List<Book> allBooks = bookDaoJdbc.getAll();
        assertAll(() -> assertThat(allBooks).hasSize(EXPECTED_BOOKS_COUNT - 1),
                () -> assertThat(allBooks.stream()).noneMatch(book -> book.getId().equals(LONG_WALK_ID)));
    }
}
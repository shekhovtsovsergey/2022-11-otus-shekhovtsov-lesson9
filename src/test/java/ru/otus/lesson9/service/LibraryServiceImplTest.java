package ru.otus.lesson9.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.otus.lesson9.dao.AuthorDao;
import ru.otus.lesson9.dao.BookDao;
import ru.otus.lesson9.dao.GenreDao;
import ru.otus.lesson9.model.Author;
import ru.otus.lesson9.model.Book;
import ru.otus.lesson9.model.Genre;
import static org.mockito.Mockito.verify;


@SpringBootTest
@DisplayName("Сервис библиотеки должен")
class LibraryServiceImplTest {

    @Configuration
    @Import(LibraryServiceImpl.class)
    static class NestedConfiguration {
    }

    @MockBean
    private AuthorDao authorDao;
    @MockBean
    private GenreDao genreDao;
    @MockBean
    private BookDao bookDao;

    @Autowired
    private LibraryService libraryService;

    @Test
    @DisplayName("корректно вызывать authorDao")
    void getAllAuthors() {
        libraryService.getAllAuthors();
        verify(authorDao).getAll();
    }

    @Test
    @DisplayName("корректно вызывать genreDao")
    void getAllGenres() {
        libraryService.getAllGenres();
        verify(genreDao).getAll();
    }

    @Test
    @DisplayName("корректно вызывать bookDao.count")
    void booksCount() {
        libraryService.booksCount();
        verify(bookDao).count();
    }

    @Test
    @DisplayName("корректно вызывать bookDao.insert")
    void insertBook() {
        Book book = new Book(null, null, new Author(null, null), new Genre(null, null));
        libraryService.insertBook(book);
        verify(bookDao).insert(book);
    }

    @Test
    @DisplayName("корректно вызывать bookDao.getById")
    void getBookById() {
        libraryService.getBookById(1L);
        verify(bookDao).getById(1L);
    }

    @Test
    @DisplayName("корректно вызывать bookDao.getAll")
    void getAllBooks() {
        libraryService.getAllBooks();
        verify(bookDao).getAll();
    }

    @Test
    @DisplayName("корректно вызывать bookDao.getAllByAuthor")
    void getAllBooksByAuthor() {
        Author author = new Author(null, null);
        libraryService.getAllBooksByAuthor(author);
        verify(bookDao).getAllByAuthor(author);
    }

    @Test
    @DisplayName("корректно вызывать bookDao.getAllByGenre")
    void getAllBooksByGenre() {
        Genre genre = new Genre(null, null);
        libraryService.getAllBooksByGenre(genre);
        verify(bookDao).getAllByGenre(genre);
    }

    @Test
    @DisplayName("корректно вызывать bookDao.getAllByAuthorAndGenre")
    void getAllBooksByAuthorAndGenre() {
        Author author = new Author(null, null);
        Genre genre = new Genre(null, null);
        libraryService.getAllBooksByAuthorAndGenre(author, genre);
        verify(bookDao).getAllByAuthorAndGenre(author, genre);
    }

    @Test
    @DisplayName("корректно вызывать bookDao.updateById")
    void updateBookById() {
        Author author = new Author(2L, null);
        Genre genre = new Genre(3L, null);
        libraryService.updateBookById(1L, "newName", author, genre);
        verify(bookDao).updateById(1L, "newName", author.getId(), genre.getId());
    }

    @Test
    @DisplayName("корректно вызывать bookDao.deleteById")
    void deleteBooksById() {
        libraryService.deleteBooksById(1L);
        verify(bookDao).deleteById(1L);
    }
}
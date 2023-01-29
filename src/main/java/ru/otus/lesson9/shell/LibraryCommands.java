package ru.otus.lesson9.shell;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.lesson9.model.Author;
import ru.otus.lesson9.model.Book;
import ru.otus.lesson9.model.Genre;
import ru.otus.lesson9.service.LibraryService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class LibraryCommands {
    private final LibraryService libraryService;

    @ShellMethod(value = "Get all authors command", key = {"authors"})
    public String authors() {
        List<Author> allAuthors = libraryService.getAllAuthors();
        return String.format("Все авторы библиотеки: %s", allAuthors);
    }

    @ShellMethod(value = "Get all genres command", key = {"genres"})
    public String genres() {
        libraryService.getAllGenres();
        List<Genre> allGenre = libraryService.getAllGenres();
        return String.format("Все жанры библиотеки: %s", allGenre);
    }

    @ShellMethod(value = "Get books count", key = {"booksCount"})
    public int count() {
        return libraryService.booksCount();
    }

    @ShellMethod(value = "Insert book command", key = {"insertBook"})
    public void insertBook(@ShellOption String bookName, @ShellOption Long authorId, @ShellOption Long genreId) {
        libraryService.insertBook(new Book(null, bookName, new Author(authorId, null), new Genre(genreId, null)));
    }

    @ShellMethod(value = "Get books by id", key = {"bookById"})
    public String getBookById(@ShellOption Long bookId) {
        Book book = libraryService.getBookById(bookId);
        return String.format("Вы взяли книгу: %s", book);
    }

    @ShellMethod(value = "Get all books", key = {"allBooks"})
    public String getAllBooks() {
        List<Book> allBooks = libraryService.getAllBooks();
        return String.format("Все книги библиотеки: %s", allBooks);
    }

    @ShellMethod(value = "Get books by author id", key = {"booksByAuthorId"})
    public String getAllBooksByAuthor(@ShellOption Long authorId) {
        List<Book> allBooksByAuthor = libraryService.getAllBooksByAuthor(new Author(authorId, null));
        return String.format("Вы взяли следующие книги по автору: %s", allBooksByAuthor);
    }

    @ShellMethod(value = "Get books by genre id", key = {"booksByGenreId"})
    public String getAllBooksByGenre(@ShellOption Long genreId) {
        List<Book> allBooksByGenre = libraryService.getAllBooksByGenre(new Genre(genreId, null));
        return String.format("Вы взяли следующие книги по жанру: %s", allBooksByGenre);
    }

    @ShellMethod(value = "Get books by author id and genre id", key = {"booksByAuthorIdAndGenreId"})
    public String getAllBooksByAuthorAndGenre(@ShellOption Long authorId, @ShellOption Long genreId) {
        List<Book> allBooksByAuthorAndGenre = libraryService.getAllBooksByAuthorAndGenre(
                new Author(authorId, null),
                new Genre(genreId, null));
        return String.format("Вы взяли следующие книги по автору и жанру: %s", allBooksByAuthorAndGenre);
    }

    @ShellMethod(value = "Update book by id", key = {"updateBook"})
    public void updateBookById(@ShellOption Long id, @ShellOption String bookName, @ShellOption Long authorId, @ShellOption Long genreId) {
        libraryService.updateBookById(id, bookName, new Author(authorId, null), new Genre(genreId, null));
    }

    @ShellMethod(value = "Delete book by id", key = {"deleteBookById"})
    public String deleteBooksById(@ShellOption Long bookId) {
        libraryService.deleteBooksById(bookId);
        return String.format("Книга удалена: %s", bookId);
    }
}

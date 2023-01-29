package ru.otus.lesson9.dao;

import ru.otus.lesson9.model.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(Long id);

    List<Author> getAll();



}

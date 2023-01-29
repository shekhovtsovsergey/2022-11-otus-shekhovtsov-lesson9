package ru.otus.lesson9.dao;


import ru.otus.lesson9.model.Genre;

import java.util.List;

public interface GenreDao {


    Genre getById(Long id);

    List<Genre> getAll();

}

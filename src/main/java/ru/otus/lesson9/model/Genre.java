package ru.otus.lesson9.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class Genre {

    private final Long id;
    private final String name;

}

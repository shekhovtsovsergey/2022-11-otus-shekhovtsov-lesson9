insert into author (id, name) values (1, 'Lean Nielsen'),
                                     (2, 'Klaus Rifbjerg'),
                                     (3, 'Thorkild Bjørnvig');

insert into genre (id, name) values (1, 'Comedy'),
                                    (2, 'Comedy'),
                                    (3, 'Comedy'),
                                    (4, 'Comedy');

insert into book (id, name, author_id, genre_id) values (1, 'Ned ad trappen, ud på gaden (Danish Edition)', 2, 4),
                                                        (2, 'Ned ad trappen, ud på gaden (Danish Edition)', 2, 4),
                                                        (3, 'Ned ad trappen, ud på gaden (Danish Edition)', 1, 1),
                                                        (4, 'Ned ad trappen, ud på gaden (Danish Edition)', 3, 2),
                                                        (5, 'Ned ad trappen, ud på gaden (Danish Edition)', 3, 3);

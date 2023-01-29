drop table if exists book;
drop table if exists genre;
drop table if exists author;

create table author(id bigint not null auto_increment,
                    name varchar(255) not null,
                    primary key (id));

create table genre(id bigint not null auto_increment,
                    name varchar(255) not null,
                    primary key (id));

create table book(id bigint not null auto_increment,
                  name varchar(255) not null,
                  author_id bigint not null,
                  genre_id bigint not null,
                  primary key (id),
                  foreign key (author_id) references author(id),
                  foreign key (genre_id) references genre(id));

create table authors (
    id identity primary key,
    name varchar(255) not null,
    date_of_birth date
);

create table genres (
    id identity primary key,
    name varchar(255) not null
);

create table books (
    id identity primary key,
    name varchar(255) not null,
    release_year int,
    author_id bigint not null,
    foreign key (author_id) references authors(id)
);

create table genres_books (
    genre_id bigint not null,
    book_id bigint not null,
    primary key (genre_id, book_id),
    foreign key (genre_id) references genres(id),
    foreign key (book_id) references  books(id)
);
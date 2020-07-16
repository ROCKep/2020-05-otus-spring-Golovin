insert into genres (name)
values ('test genre 1'),
       ('test genre 2');

insert into authors (name, date_of_birth)
values ('test author', '1991-12-31');

insert into books (name, release_year, author_id)
values ('test book 1', 1995, 1),
       ('test book 2', null, 1);

insert into genres_books (genre_id, book_id)
values (1, 1),
       (2, 2);
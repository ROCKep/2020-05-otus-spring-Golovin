insert into genres (name)
values ('Антиутопия'),
       ('Фэнтези'),
       ('Классика');

insert into authors (name, date_of_birth)
values ('Джордж Оруэлл', '1903-06-25'),
       ('Джоан Роулинг', '1965-07-31'),
       ('Михаил Булгаков', null);

insert into books (name, release_year, author_id)
    select '1984' name, 1949 release_year, id author_id from authors where name = 'Джордж Оруэлл' union
    select 'Гарри Поттер и философский камень', null, id from authors where name = 'Джоан Роулинг' union
    select 'Мастер и Маргарита', 1966, id from authors where name = 'Михаил Булгаков';

insert into genres_books (genre_id, book_id)
    select g.id genre_id, b.id book_id
    from genres g, books b
    where g.name = 'Антиутопия' and b.name = '1984'
       or g.name = 'Классика' and b.name = '1984'
       or g.name = 'Фэнтези' and b.name = 'Гарри Поттер и философский камень'
       or g.name = 'Классика' and b.name = 'Мастер и Маргарита';
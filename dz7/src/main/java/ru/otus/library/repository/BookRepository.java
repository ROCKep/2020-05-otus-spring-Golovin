package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.library.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b join fetch b.genres join fetch b.author where b.id = :id")
    Book getByIdWithDetails(@Param("id") long id);
    Book getById(long id);
    int removeById(long id);
}

package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.library.domain.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b join fetch b.genres join fetch b.author where b.id = :id")
    Optional<Book> findByIdWithDetails(@Param("id") long id);
}

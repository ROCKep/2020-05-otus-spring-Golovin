package ru.otus.library.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Author getByName(String name);

}

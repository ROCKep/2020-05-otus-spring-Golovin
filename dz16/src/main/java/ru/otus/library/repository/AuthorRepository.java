package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findByName(String name);

}

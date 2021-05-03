package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Mono<Book> findByName(String name);
}

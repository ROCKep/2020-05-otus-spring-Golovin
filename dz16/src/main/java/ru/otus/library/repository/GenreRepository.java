package ru.otus.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.library.domain.Genre;

import java.util.List;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
    Flux<Genre> findByNameIn(List<String> names);
}
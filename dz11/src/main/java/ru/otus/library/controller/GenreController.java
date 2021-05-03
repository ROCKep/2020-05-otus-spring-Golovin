package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping("/api/genres")
    public Flux<Genre> listGenres() {
        return genreRepository.findAll();
    }

}

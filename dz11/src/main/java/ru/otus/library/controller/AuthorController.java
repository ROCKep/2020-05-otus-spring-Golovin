package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.library.domain.Author;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.repository.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping("/api/authors")
    public Flux<AuthorDto> listAuthors() {
        Flux<Author> authors = authorRepository.findAll();
        return authors
                .map(author -> new AuthorDto(author.getId(), author.getName()));
    }
}

package ru.otus.library.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByName() {
        StepVerifier
                .create(bookRepository.findByName("test book 1"))
                .assertNext(book -> assertEquals("test book 1", book.getName()))
                .expectComplete()
                .verify();
    }
}

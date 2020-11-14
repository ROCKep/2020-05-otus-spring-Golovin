package ru.otus.library.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testShortString() {
        String expected = "1. test book (1965)";
        Book book = new Book(1, "test book", 1965);
        assertEquals(expected, book.shortString());
    }

    @Test
    void testLongString() {
        String expected = String.format("1. test book (1965)%n" +
                "\tAuthor: test author%n" +
                "\tGenres: test genre 1, test genre 2");
        Author author = new Author(1L, "test author", null);
        List<Genre> genres = Arrays.asList(
                new Genre(1L, "test genre 1"),
                new Genre(2L, "test genre 2")
        );
        Book book = new Book(1L, "test book", 1965, genres, author);
        assertEquals(expected, book.longString());
    }
}
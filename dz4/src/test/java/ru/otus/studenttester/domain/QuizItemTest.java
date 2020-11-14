package ru.otus.studenttester.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizItemTest {
    @Test
    public void testGetStringForDisplay() {
        QuizItem item = new QuizItem("Test Question", Arrays.asList("Test Answer 1", "Test Answer 2"), 0);
        String actual = item.getStringForDisplay();
        assertEquals(String.format("Test Question%n\t1. Test Answer 1%n\t2. Test Answer 2"), actual);
    }
}

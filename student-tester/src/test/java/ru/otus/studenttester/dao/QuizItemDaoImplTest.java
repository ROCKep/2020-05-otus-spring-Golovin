package ru.otus.studenttester.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.studenttester.domain.QuizItem;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class QuizItemDaoImplTest {

    @Configuration
    @ComponentScan({"ru.otus.studenttester.dao", "ru.otus.studenttester.utils"})
    static class TestConfig {

    }

    @Autowired
    private QuizItemDao dao;

    @Test
    public void testListQuizItems() {
        List<QuizItem> expected = Arrays.asList(
                new QuizItem("Question 1?", Arrays.asList("Answer 10", "Answer 11", "Answer 12"), 0),
                new QuizItem("Question 2?", Arrays.asList("Answer 20", "Answer 21", "Answer 22"), 1)
        );
        List<QuizItem> actual = dao.listQuizItems();
        Assertions.assertIterableEquals(expected, actual);
    }
}
package ru.otus.studenttester.service;

import org.junit.jupiter.api.Test;
import ru.otus.studenttester.domain.QuizItem;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizServiceImplTest {

    @Test
    public void testCheckQuizResult() {
        QuizService service = new QuizServiceImpl(null, null, null);
        List<QuizItem> testQuiz = Arrays.asList(
                new QuizItem("Question 1?", Arrays.asList("Answer 10", "Answer 11", "Answer 12"), 0),
                new QuizItem("Question 2?", Arrays.asList("Answer 20", "Answer 21", "Answer 22"), 1)
        );
        List<Integer> testAnswers = Arrays.asList(0, 1);
        int actual = service.checkQuizResult(testQuiz, testAnswers);
        assertEquals(2, actual);
    }

}
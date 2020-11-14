package ru.otus.studenttester.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.studenttester.dao.QuizItemDaoImpl;
import ru.otus.studenttester.domain.QuizItem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizItemDaoImpl dao;

    @Test
    void testPerformQuiz() {
        String testInput = "Test name\n1\n2\n1\n";
        ScanService scanService = new ScanServiceImpl(new ByteArrayInputStream(testInput.getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintService printService = new PrintServiceImpl(new PrintStream(baos));

        doReturn(Arrays.asList(
                new QuizItem("Question 1?", Arrays.asList("Answer1", "Answer2"), (short) 0),
                new QuizItem("Question 2?", Arrays.asList("Answer1", "Answer2"), (short) 1),
                new QuizItem("Question 3?", Arrays.asList("Answer1", "Answer2"), (short) 1)
        )).when(dao).listQuizItems();

        QuizService instance = new QuizService(2, printService, scanService, dao);
        instance.performQuiz();

        verify(dao).listQuizItems();
        assertThat(baos.toString()).contains("Passed!").contains("2/3");
    }
}
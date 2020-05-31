package ru.otus.studenttester.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.studenttester.domain.QuizItem;
import ru.otus.studenttester.utils.CsvUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuizItemDaoImplTest {
    @Mock
    CsvUtils csvUtils;

    @Test
    public void testListQuizItems() {
        QuizItem testQuizItem = new QuizItem("Question?", Arrays.asList("Answer1", "Answer2"), (short) 1);
        Mockito.doReturn(Collections.singletonList(testQuizItem))
                .when(csvUtils).readCsv(Mockito.any(), Mockito.any());
        QuizItemDao instance = new QuizItemDaoImpl("ignored", csvUtils);
        List<QuizItem> result = instance.listQuizItems();
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.size(), 1));
    }
}
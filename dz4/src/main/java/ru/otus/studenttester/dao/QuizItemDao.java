package ru.otus.studenttester.dao;

import ru.otus.studenttester.domain.QuizItem;

import java.util.List;

/**
 * DAO для чтения вопросов
 */
public interface QuizItemDao {
    /**
     * Получить все вопросы
     */
    List<QuizItem> listQuizItems();
}

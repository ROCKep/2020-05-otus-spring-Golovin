package ru.otus.studenttester.service;

import ru.otus.studenttester.domain.QuizItem;

import java.util.List;

public interface QuizService {
    String getStudentName();
    int getStudentAnswer(QuizItem item);
    int checkQuizResult(List<QuizItem> quizItems, List<Integer> studentAnswers);
    void showResult(String studentName, boolean passed, int correctCount, int totalCount);
}

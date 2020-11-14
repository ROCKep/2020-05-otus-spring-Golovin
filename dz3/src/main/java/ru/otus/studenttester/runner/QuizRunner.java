package ru.otus.studenttester.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.studenttester.dao.QuizItemDao;
import ru.otus.studenttester.domain.QuizItem;
import ru.otus.studenttester.service.QuizService;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizRunner {

    private final int minCorrectAnswers;
    private final QuizItemDao dao;
    private final QuizService service;

    public QuizRunner(@Value("${app.min-correct-answers}") int minCorrectAnswers, QuizItemDao dao, QuizService service) {
        this.minCorrectAnswers = minCorrectAnswers;
        this.dao = dao;
        this.service = service;
    }

    public void run() {
        // Считать вопросы
        List<QuizItem> quizItems = dao.listQuizItems();
        // Спросить имя
        String studentName = service.getStudentName();
        // Цикл по вопросам
        List<Integer> studentAnswers = new ArrayList<>();
        for (QuizItem item : quizItems) {
            // Вывести вопрос
            // Спросить ответ
            studentAnswers.add(service.getStudentAnswer(item));
        }
        // Вывести результат
        int correctAnswers = service.checkQuizResult(quizItems, studentAnswers);
        boolean passed = correctAnswers >= minCorrectAnswers;
        service.showResult(studentName, passed, correctAnswers, studentAnswers.size());
    }
}

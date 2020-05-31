package ru.otus.studenttester.service;

import ru.otus.studenttester.domain.QuizItem;
import ru.otus.studenttester.dao.QuizItemDao;

import java.util.List;

public class QuizItemServiceImpl implements QuizItemService {
    private final QuizItemDao dao;

    public QuizItemServiceImpl(QuizItemDao dao) {
        this.dao = dao;
    }

    @Override
    public void displayQuizItems() {
        List<QuizItem> quizItems = dao.listQuizItems();
        quizItems.forEach(quizItem ->
                System.out.println(String.format("Question: %s; answers: %s; correct: %d",
                        quizItem.getQuestion(), quizItem.getAnswers(), quizItem.getCorrectNum())));
    }
}

package ru.otus.studenttester.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.studenttester.dao.QuizItemDao;
import ru.otus.studenttester.domain.QuizItem;

import java.util.List;

@Service
public class QuizService {

    private final int minCorrectAnswers;

    private final PrintService printService;
    private final ScanService scanService;
    private final QuizItemDao dao;

    public QuizService(@Value("${min.correct.answers}") int minCorrectAnswers,
                       PrintService printService,
                       ScanService scanService,
                       QuizItemDao dao) {
        this.minCorrectAnswers = minCorrectAnswers;

        this.printService = printService;
        this.scanService = scanService;
        this.dao = dao;
    }

    public void performQuiz() {
        String name = askForName();
        List<QuizItem> quizItems = dao.listQuizItems();
        int correctCount = 0;
        for (QuizItem quizItem : quizItems) {
            showQuizItem(quizItem);
            short answerNum = askForAnswerNum();
            if (answerNum == quizItem.getCorrectNum()) {
                correctCount++;
            }
        }
        showResult(name, correctCount, quizItems.size());
    }

    private String askForName() {
        String name;
        do {
            printService.print("Please enter your name: ");
            name = scanService.scanLine();
        } while (name.isBlank());
        return name;
    }

    private void showQuizItem(QuizItem quizItem) {
        printService.printLine(quizItem.getQuestion());
        List<String> answers = quizItem.getAnswers();
        for (int answerNum = 0; answerNum < answers.size(); answerNum++) {
            printService.printLine(String.format("\t%d. %s", answerNum + 1, answers.get(answerNum)));
        }
    }

    private short askForAnswerNum() {
        printService.print("Enter answer number: ");
        return (short) (scanService.scanShort() - 1);
    }

    private void showResult(String name, int correctCount, int totalCount) {
        String result = correctCount >= minCorrectAnswers ? "Passed" : "Failed";
        printService.printLine(String.format("%s, here are your results.", name));
        printService.printLine(String.format("%s! Correct answers: %d/%d", result, correctCount, totalCount));
    }
}

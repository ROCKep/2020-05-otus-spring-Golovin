package ru.otus.studenttester.service;

import org.springframework.stereotype.Service;
import ru.otus.studenttester.domain.QuizItem;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final PrintService printService;
    private final ScanService scanService;
    private final MessageService messageService;

    public QuizServiceImpl(PrintService printService,
                           ScanService scanService, MessageService messageService) {
        this.printService = printService;
        this.scanService = scanService;
        this.messageService = messageService;
    }

    @Override
    public String getStudentName() {
        String name;
        do {
            printService.print(String.format("%s: ", messageService.getMessage("enter.name")));
            name = scanService.scanLine();
        } while (name.isBlank());
        return name;
    }

    @Override
    public int getStudentAnswer(QuizItem item) {
        printService.printLine(item.getStringForDisplay());
        printService.print(String.format("%s: ", messageService.getMessage("enter.answer")));
        return scanService.scanInt() - 1;
    }

    @Override
    public int checkQuizResult(List<QuizItem> quizItems, List<Integer> studentAnswers) {
        int correctAnswers = 0;
        for (int i = 0; i < quizItems.size(); i++) {
            if (quizItems.get(i).getCorrectNum() == studentAnswers.get(i)) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    @Override
    public void showResult(String studentName, boolean passed, int correctCount, int totalCount) {
        String result = messageService.getMessage(passed ? "passed" : "failed");
        printService.printLine(String.format("%s, %s: %s/%s", studentName, messageService.getMessage("result", result), correctCount, totalCount));
    }
}

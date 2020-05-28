package ru.otus.studenttester.domain;

import java.util.List;

public class QuizItem {
    private final String question;
    private final List<String> answers;
    private final short correctNum;

    public QuizItem(String question, List<String> answers, short correctNum) {
        this.question = question;
        this.answers = answers;
        this.correctNum = correctNum;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public short getCorrectNum() {
        return correctNum;
    }
}

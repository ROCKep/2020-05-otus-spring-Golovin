package ru.otus.studenttester.domain;

import java.util.List;
import java.util.Objects;

public class QuizItem {
    private final String question;
    private final List<String> answers;
    private final int correctNum;

    public QuizItem(String question, List<String> answers, int correctNum) {
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

    public int getCorrectNum() {
        return correctNum;
    }

    public String getStringForDisplay() {
        StringBuilder builder = new StringBuilder(question);
        for (int answerNum = 0; answerNum < answers.size(); answerNum++) {
            builder.append(String.format("%n\t%d. %s", answerNum + 1, answers.get(answerNum)));
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizItem quizItem = (QuizItem) o;
        return correctNum == quizItem.correctNum &&
                question.equals(quizItem.question) &&
                answers.equals(quizItem.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answers, correctNum);
    }
}

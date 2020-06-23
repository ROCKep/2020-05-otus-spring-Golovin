package ru.otus.studenttester;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.studenttester.runner.QuizRunner;

@ShellComponent
public class QuizShell {

    private final QuizRunner quizRunner;

    public QuizShell(QuizRunner quizRunner) {
        this.quizRunner = quizRunner;
    }

    @ShellMethod(value = "Run quiz", key = {"r", "run"})
    public void runQuiz() {
        quizRunner.run();
    }
}

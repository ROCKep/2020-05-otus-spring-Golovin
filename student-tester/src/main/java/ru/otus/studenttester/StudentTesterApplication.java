package ru.otus.studenttester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.studenttester.runner.QuizRunner;

@SpringBootApplication
public class StudentTesterApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StudentTesterApplication.class, args);
        QuizRunner runner = context.getBean(QuizRunner.class);
        runner.run();
    }

}

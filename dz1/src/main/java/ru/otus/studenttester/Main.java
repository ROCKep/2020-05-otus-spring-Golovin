package ru.otus.studenttester;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.studenttester.service.QuizItemService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizItemService service = context.getBean(QuizItemService.class);
        service.displayQuizItems();
    }
}

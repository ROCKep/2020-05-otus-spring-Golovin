package ru.otus.jpp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.jpp.service.AppService;


@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(App.class, args);

        AppService appService = ctx.getBean(AppService.class);
        appService.execute();
    }
}

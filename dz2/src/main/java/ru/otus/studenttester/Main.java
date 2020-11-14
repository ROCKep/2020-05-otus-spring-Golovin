package ru.otus.studenttester;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import ru.otus.studenttester.service.*;

@Configuration
@ComponentScan
public class Main {
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setLocations(new ClassPathResource("application.properties"));
        return pspc;
    }

    @Bean
    public PrintService printService() {
        return new PrintServiceImpl(System.out);
    }

    @Bean
    public ScanService scanService() {
        return new ScanServiceImpl(System.in);
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuizService service = context.getBean(QuizService.class);

        service.performQuiz();
    }
}

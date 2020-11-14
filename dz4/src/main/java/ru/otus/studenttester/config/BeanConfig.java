package ru.otus.studenttester.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.studenttester.service.PrintService;
import ru.otus.studenttester.service.PrintServiceImpl;
import ru.otus.studenttester.service.ScanService;
import ru.otus.studenttester.service.ScanServiceImpl;

@Configuration
public class BeanConfig {

    @Bean
    public PrintService printService() {
        return new PrintServiceImpl(System.out);
    }

    @Bean
    public ScanService scanService() {
        return new ScanServiceImpl(System.in);
    }

}

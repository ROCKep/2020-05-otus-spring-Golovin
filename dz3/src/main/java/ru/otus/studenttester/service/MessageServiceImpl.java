package ru.otus.studenttester.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {
    private final Locale locale;
    private final MessageSource messageSource;

    public MessageServiceImpl(@Value("${app.locale}") Locale locale, MessageSource messageSource) {
        this.locale = locale;
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, locale);
    }

    @Override
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, locale);
    }
}

package ru.otus.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String msg) {
        super(msg);
    }
}
package ru.otus.studenttester.exception;

public class CsvUtilsException extends RuntimeException {
    public CsvUtilsException(String message) {
        super(message);
    }

    public CsvUtilsException(String message, Throwable cause) {
        super(message, cause);
    }
}

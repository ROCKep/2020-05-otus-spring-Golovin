package ru.otus.studenttester.service;

import java.io.PrintStream;

public class PrintServiceImpl implements PrintService {
    private final PrintStream printStream;

    public PrintServiceImpl(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void print(String str) {
        printStream.print(str);
    }

    public void printLine(String str) {
        printStream.println(str);
    }
}

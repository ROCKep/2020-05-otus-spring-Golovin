package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceConsole implements IOService {

    private final PrintStream printStream;
    private final Scanner scanner;

    public IOServiceConsole(@Value("#{ T(java.lang.System).out}") PrintStream out,
                            @Value("#{ T(java.lang.System).in}") InputStream in) {
        this.printStream = out;
        this.scanner = new Scanner(in);
    }

    @Override
    public void output(String s) {
        printStream.print(s);
    }

    @Override
    public void outputLine(String s) {
        printStream.println(s);
    }

    @Override
    public String inputLine() {
        return scanner.nextLine();
    }

    @Override
    public Integer inputInteger() {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return null;
        }
        return Integer.parseInt(input);
    }

}

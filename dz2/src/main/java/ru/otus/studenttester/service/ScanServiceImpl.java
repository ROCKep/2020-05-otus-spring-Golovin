package ru.otus.studenttester.service;

import java.io.InputStream;
import java.util.Scanner;

public class ScanServiceImpl implements ScanService {

    private final Scanner scanner;

    public ScanServiceImpl(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public String scanLine() {
        return scanner.nextLine();
    }

    public short scanShort() {
        return scanner.nextShort();
    }
}

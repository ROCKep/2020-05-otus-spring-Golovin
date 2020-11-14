package ru.otus.studenttester.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintServiceImplTest {
    @Test
    public void testPrint() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintService service = new PrintServiceImpl(new PrintStream(baos));

        service.print("Test");
        assertEquals("Test", baos.toString());
    }

    @Test
    public void testPrintLine() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintService service = new PrintServiceImpl(new PrintStream(baos));

        service.printLine("Test");
        assertEquals(String.format("Test%n"), baos.toString());
    }
}

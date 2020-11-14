package ru.otus.library.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IOServiceConsoleTest {
    @Test
    public void testOutput() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOService service = new IOServiceConsole(new PrintStream(baos), System.in);

        service.output("Test");
        assertEquals("Test", baos.toString());
    }

    @Test
    public void testOutputLine() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOService service = new IOServiceConsole(new PrintStream(baos), System.in);

        service.outputLine("Test");
        assertEquals(String.format("Test%n"), baos.toString());
    }

    @Test
    public void testInputLine() {
        String testInput = String.format("Test test 1%nTest test 2");
        IOService service = new IOServiceConsole(System.out, new ByteArrayInputStream(testInput.getBytes()));
        String actual1 = service.inputLine();
        String actual2 = service.inputLine();
        assertEquals("Test test 1", actual1);
        assertEquals("Test test 2", actual2);
    }

    @Test
    public void testInputInteger() {
        String testInput = String.format("1%n2%n");
        IOService service = new IOServiceConsole(System.out, new ByteArrayInputStream(testInput.getBytes()));
        int actual1 = service.inputInteger();
        int actual2 = service.inputInteger();
        assertEquals(1, actual1);
        assertEquals(2, actual2);
    }
}

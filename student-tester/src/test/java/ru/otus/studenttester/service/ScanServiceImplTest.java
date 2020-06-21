package ru.otus.studenttester.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScanServiceImplTest {
    @Test
    public void testScanLine() {
        String testInput = String.format("Test test 1%nTest test 2");
        ScanService service = new ScanServiceImpl(new ByteArrayInputStream(testInput.getBytes()));
        String actual1 = service.scanLine();
        String actual2 = service.scanLine();
        assertEquals("Test test 1", actual1);
        assertEquals("Test test 2", actual2);
    }

    @Test
    public void testScanInt() {
        String testInput = String.format("1 2%n");
        ScanService service = new ScanServiceImpl(new ByteArrayInputStream(testInput.getBytes()));
        int actual1 = service.scanInt();
        int actual2 = service.scanInt();
        assertEquals(1, actual1);
        assertEquals(2, actual2);
    }
}

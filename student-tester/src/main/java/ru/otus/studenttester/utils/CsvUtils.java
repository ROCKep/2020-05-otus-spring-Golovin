package ru.otus.studenttester.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvUtils {

    public <T> List<T> readCsv(String filename, Function<String[], T> mapper) {
        InputStream stream = getClass().getResourceAsStream(filename);
        if (stream == null) {
            throw new CsvUtilsException("Resource not found");
        }

        try (CSVReader reader = new CSVReaderHeaderAware(new InputStreamReader(stream))) {
            List<T> objs = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                T obj = mapper.apply(line);
                objs.add(obj);
            }
            return objs;
        } catch (IOException | CsvValidationException e) {
            throw new CsvUtilsException("CSV read error", e);
        }
    }

    public static class CsvUtilsException extends RuntimeException {
        public CsvUtilsException(String message) {
            super(message);
        }

        public CsvUtilsException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

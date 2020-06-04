package ru.otus.studenttester.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.studenttester.domain.QuizItem;
import ru.otus.studenttester.utils.CsvUtils;

import java.util.Arrays;
import java.util.List;

@Repository
public class QuizItemDaoImpl implements QuizItemDao {

    private final String filename;
    private final CsvUtils csvUtils;

    public QuizItemDaoImpl(@Value("${path.to.file}") String filename, CsvUtils csvUtils) {
        this.filename = filename;
        this.csvUtils = csvUtils;
    }

    public List<QuizItem> listQuizItems() {
        return csvUtils.readCsv(filename, line -> {
            String question = line[0];
            List<String> answers = Arrays.asList(line[1], line[2], line[3]);
            short correctNum = Short.parseShort(line[4]);
            return new QuizItem(question, answers, correctNum);
        });
    }
}

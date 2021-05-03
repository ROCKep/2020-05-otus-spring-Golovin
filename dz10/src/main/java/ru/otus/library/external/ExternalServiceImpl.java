package ru.otus.library.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ExternalServiceImpl implements ExternalService {
    @Override
    @HystrixCommand(fallbackMethod = "fallbackFindBooks")
    public List<ExternalBookDto> findExternalBooks(String name) {
        sleepRandomly();
        String resource = "https://www.googleapis.com/books/v1/volumes?q=" + name;
        return ExternalReader.readExternal(resource, root -> {
            ArrayNode items = (ArrayNode) root.get("items");
            List<ExternalBookDto> books = new ArrayList<>();
            for (JsonNode item : items) {
                JsonNode volumeInfo = item.get("volumeInfo");
                List<String> authors = new ArrayList<>();
                volumeInfo.get("authors").elements().forEachRemaining(author ->
                        authors.add(author.asText()));

                ExternalBookDto bookDto = new ExternalBookDto(
                        item.get("id").asText(),
                        volumeInfo.get("title").asText(),
                        authors);
                books.add(bookDto);
            }
            return books;
        });
    }

    private void sleepRandomly() {
        try {
            int ms = (int) (Math.random() * 5000);
            System.out.println("Sleeping for " + ms + " ms");
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted", e);
        }
    }

    private List<ExternalBookDto> fallbackFindBooks(String name) {
        return Collections.emptyList();
    }
}

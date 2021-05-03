package ru.otus.library.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

@UtilityClass
public class ExternalReader {

    public <T> T readExternal(String url, Function<JsonNode, T> mapper) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response
                    = restTemplate.getForEntity(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            return mapper.apply(root);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Ошибка парсинга JSON", ex);
        }
    }
}

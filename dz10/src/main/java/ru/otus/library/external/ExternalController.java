package ru.otus.library.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExternalController {

    private final ExternalService externalService;

    @GetMapping("/api/external/find")
    public List<ExternalBookDto> findExternalBooks(@RequestParam String name) {
        return externalService.findExternalBooks(name);
    }
}

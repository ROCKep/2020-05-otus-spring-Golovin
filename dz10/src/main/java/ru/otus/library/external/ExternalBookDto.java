package ru.otus.library.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ExternalBookDto {
    private String id;
    private String name;
    private List<String> authors;
}

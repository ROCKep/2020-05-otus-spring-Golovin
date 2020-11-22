package ru.otus.library.external;

import java.util.List;

public interface ExternalService {
    List<ExternalBookDto> findExternalBooks(String name);
}

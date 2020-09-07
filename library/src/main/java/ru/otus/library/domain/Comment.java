package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private String id;

    private String content;

    private String user;

    @DBRef
    private Book book;

    public Comment(String content, String user, Book book) {
        this(null, content, user, book);
    }

    public Comment(String content, String user) {
        this(content, user, null);
    }
}

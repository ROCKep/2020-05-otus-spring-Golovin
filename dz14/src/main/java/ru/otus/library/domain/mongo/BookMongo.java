package ru.otus.library.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookMongo {
    @Id
    private String id;
    private String name;
    private Integer releaseYear;

    @DBRef
    private List<GenreMongo> genres;

    @DBRef
    private AuthorMongo author;
}

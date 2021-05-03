package ru.otus.library.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document(collection = "genre")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreMongo {
    @Id
    private String id;
    private String name;
}

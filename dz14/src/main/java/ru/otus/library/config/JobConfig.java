package ru.otus.library.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.library.domain.jpa.AuthorJpa;
import ru.otus.library.domain.jpa.BookJpa;
import ru.otus.library.domain.jpa.GenreJpa;
import ru.otus.library.domain.mongo.AuthorMongo;
import ru.otus.library.domain.mongo.BookMongo;
import ru.otus.library.domain.mongo.GenreMongo;

import javax.persistence.EntityManagerFactory;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
public class JobConfig {
    @Bean
    ItemReader<AuthorJpa> authorReader(EntityManagerFactory emf) {
        return new JpaPagingItemReaderBuilder<AuthorJpa>()
                .name("authorReader")
                .entityManagerFactory(emf)
                .pageSize(1)
                .queryString("select a from AuthorJpa a")
                .build();
    }

    @Bean
    ItemProcessor<AuthorJpa, AuthorMongo> authorProcessor() {
        return aj -> new AuthorMongo(aj.getId().toString(), aj.getName(), aj.getDateOfBirth());
    }

    @Bean
    ItemWriter<AuthorMongo> authorWriter(MongoTemplate mt) {
        return new MongoItemWriterBuilder<AuthorMongo>()
                .template(mt)
                .collection("author")
                .build();
    }

    @Bean
    Step migrateAuthorsStep(StepBuilderFactory sbf, ItemReader<AuthorJpa> reader,
                            ItemProcessor<AuthorJpa, AuthorMongo> processor, ItemWriter<AuthorMongo> writer) {
        return sbf.get("migrateAuthorsStep")
                .<AuthorJpa, AuthorMongo>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    ItemReader<GenreJpa> genreReader(EntityManagerFactory emf) {
        return new JpaPagingItemReaderBuilder<GenreJpa>()
                .name("genreReader")
                .entityManagerFactory(emf)
                .pageSize(1)
                .queryString("select g from GenreJpa g")
                .build();
    }

    @Bean
    ItemProcessor<GenreJpa, GenreMongo> genreProcessor() {
        return gj -> new GenreMongo(gj.getId().toString(), gj.getName());
    }

    @Bean
    ItemWriter<GenreMongo> genreWriter(MongoTemplate mt) {
        return new MongoItemWriterBuilder<GenreMongo>()
                .template(mt)
                .collection("genre")
                .build();
    }

    @Bean
    Step migrateGenresStep(StepBuilderFactory sbf, ItemReader<GenreJpa> reader,
                            ItemProcessor<GenreJpa, GenreMongo> processor, ItemWriter<GenreMongo> writer) {
        return sbf.get("migrateGenresStep")
                .<GenreJpa, GenreMongo>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    ItemReader<BookJpa> bookReader(EntityManagerFactory emf) {
        return new JpaPagingItemReaderBuilder<BookJpa>()
                .name("bookReader")
                .entityManagerFactory(emf)
                .pageSize(1)
                .queryString("select b from BookJpa b join fetch b.genres join fetch b.author")
                .build();
    }

    @Bean
    ItemProcessor<BookJpa, BookMongo> bookProcessor() {
        return bj -> new BookMongo(bj.getId().toString(), bj.getName(), bj.getReleaseYear(),
                bj.getGenres().stream().map(gj ->
                        new GenreMongo(gj.getId().toString(), gj.getName())).collect(Collectors.toList()),
                new AuthorMongo(bj.getAuthor().getId().toString(), bj.getAuthor().getName(), bj.getAuthor().getDateOfBirth()));
    }

    @Bean
    ItemWriter<BookMongo> bookWriter(MongoTemplate mt) {
        return new MongoItemWriterBuilder<BookMongo>()
                .template(mt)
                .collection("book")
                .build();
    }

    @Bean
    Step migrateBooksStep(StepBuilderFactory sbf, ItemReader<BookJpa> reader,
                           ItemProcessor<BookJpa, BookMongo> processor, ItemWriter<BookMongo> writer) {
        return sbf.get("migrateBooksStep")
                .<BookJpa, BookMongo>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    Job migrateJob(JobBuilderFactory jbf, Step migrateAuthorsStep, Step migrateGenresStep, Step migrateBooksStep) {
        return jbf.get("migrateJob")
                .flow(migrateAuthorsStep)
                .next(migrateGenresStep)
                .next(migrateBooksStep)
                .end()
                .build();
    }
}

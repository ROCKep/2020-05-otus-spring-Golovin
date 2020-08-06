package ru.otus.library.repository;

import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> getByNames(List<String> names) {
        return em.createQuery("select g from Genre g where g.name in :names", Genre.class)
                .setParameter("names", names)
                .getResultList();

    }
}

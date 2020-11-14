package ru.otus.library.repository;

import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Author getByName(String name) {
        return em.createQuery("select a from Author a where a.name = :name", Author.class)
                .setParameter("name", name)
                .getSingleResult();

    }
}

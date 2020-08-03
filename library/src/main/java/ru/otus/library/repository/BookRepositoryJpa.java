package ru.otus.library.repository;

import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public Book getById(long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("authors-entity-graph");

        return em.createQuery("select b from Book b join fetch b.genres where b.id = :id", Book.class)
                .setParameter("id", id)
                .setHint("javax.persistence.fetchgraph", entityGraph)
                .getSingleResult();
    }

    @Override
    public Book getByIdNoDetails(long id) {
        return em.createQuery("select b from Book b where b.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Book add(Book book) {
        em.persist(book);
        em.flush();
        return book;
    }

    @Override
    public boolean delete(long id) {
        return em.createQuery("delete from Book b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }
}

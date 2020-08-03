package ru.otus.library.repository;

import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> getByBookId(long bookId) {
        return em.createQuery("select c from Comment c join fetch c.book where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        em.flush();
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        em.merge(comment);
        em.flush();
        return comment;
    }

    @Override
    public boolean delete(long id) {
        return em.createQuery("delete from Comment c where c.id = :id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    @Override
    public Comment getById(long commentId) {
        return em.createQuery("select c from Comment c where c.id = :id", Comment.class)
                .setParameter("id", commentId)
                .getSingleResult();
    }
}

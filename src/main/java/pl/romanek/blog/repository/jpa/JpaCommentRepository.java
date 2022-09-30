package pl.romanek.blog.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.repository.CommentRepository;

@Profile("jpa")
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<Comment> findAllByPostId(Integer id) {
        return em.createQuery("SELECT comment FROM Comment comment WHERE comment.post.id=" + id).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Comment> findAll() {
        return em.createQuery("SELECT comment FROM Comment comment").getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }
}

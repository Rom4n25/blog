package pl.romanek.blog.repository.jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.repository.PostRepository;

@Profile("jpa")
@Repository
public class JpaPostRepository implements PostRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> findAll() {
        return em.createQuery("SELECT post FROM Post post").getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> findAllByUserId(Integer id) {
        return em.createQuery("SELECT post FROM Post post WHERE post.user.id=" + id).getResultList();
    }

    @Override
    public Optional<Post> findById(Integer id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }
}

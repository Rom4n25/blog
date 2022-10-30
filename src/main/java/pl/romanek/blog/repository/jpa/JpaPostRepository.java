package pl.romanek.blog.repository.jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Post> findAll(Pageable page) {
        return new PageImpl<>(em
                .createQuery(
                        "SELECT DISTINCT post FROM Post post LEFT JOIN FETCH post.user user LEFT JOIN FETCH post.comment comment")
                .getResultList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> findAllByUserId(Integer id) {
        return em.createQuery(
                "SELECT DISTINCT post FROM Post post LEFT JOIN FETCH post.user user LEFT JOIN FETCH post.comment comment WHERE post.user.id="
                        + id)
                .getResultList();
    }

    @Override
    public Optional<Post> findById(Integer id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }
}

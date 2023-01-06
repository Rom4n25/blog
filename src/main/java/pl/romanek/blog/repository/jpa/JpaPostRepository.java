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
    public Page<Post> findAllByOrderByCreatedDesc(Pageable page) {
        return new PageImpl<>(em
                .createQuery(
                        "SELECT DISTINCT post FROM Post post LEFT JOIN FETCH post.user user LEFT JOIN FETCH post.comment comment ORDER BY post.created DESC")
                .setFirstResult(page.getPageNumber() * 10).setMaxResults(10)
                .getResultList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Post> findAllByUserIdOrderByCreatedDesc(Integer id, Pageable page) {
        return new PageImpl<>(em.createQuery(
                "SELECT DISTINCT post FROM Post post LEFT JOIN FETCH post.user user LEFT JOIN FETCH post.comment comment WHERE post.user.id="
                        + id + "ORDER BY post.created DESC")
                .setFirstResult(page.getPageNumber() * 10).setMaxResults(10)
                .getResultList());
    }

    @Override
    public Optional<Post> findById(Integer id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public void deleteById(Integer id) {
        Post post = em.find(Post.class, id);
        em.remove(post);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> findTop10() {
        return em.createNativeQuery(
                "SELECT post.id, post.text, post.user_id, post.created, post.last_modified, post.img FROM post, points_post WHERE post.id = points_post.post_id GROUP BY points_post.post_id ORDER BY COUNT(points_post.post_id) DESC LIMIT 10")
                .getResultList();
    }
}

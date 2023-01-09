package pl.romanek.blog.repository.jpa;

import java.util.Optional;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        Query query = em.createQuery("SELECT DISTINCT post FROM Post post ORDER BY post.created DESC");
        EntityGraph<Post> eg = em.createEntityGraph(Post.class);
        eg.addAttributeNodes("user", "pointPost");
        eg.addSubgraph("comment").addAttributeNodes("pointComment");
        query.setHint("javax.persistence.fetchgraph", eg);
        return new PageImpl<>(query
                .setFirstResult(page.getPageNumber() * 10).setMaxResults(10)
                .getResultList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Post> findAllByUserIdOrderByCreatedDesc(Integer id, Pageable page) {
        Query query = em
                .createQuery("SELECT DISTINCT post FROM Post post WHERE post.user.id="
                        + id + "ORDER BY post.created DESC");
        EntityGraph<Post> eg = em.createEntityGraph(Post.class);
        eg.addAttributeNodes("user");
        eg.addSubgraph("comment").addAttributeNodes("pointComment");
        eg.addSubgraph("pointPost").addAttributeNodes("user");
        query.setHint("javax.persistence.fetchgraph", eg);

        return new PageImpl<>(query
                .setFirstResult(page.getPageNumber() * 10).setMaxResults(10)
                .getResultList());
    }

    @Override
    public Optional<Post> findById(Integer id) {
        EntityGraph<Post> eg = em.createEntityGraph(Post.class);
        eg.addAttributeNodes("user", "pointPost");
        eg.addSubgraph("comment").addAttributeNodes("user", "pointComment");

        Query query = em.createQuery("SELECT DISTINCT post FROM Post post WHERE post.id=" + id);
        query.setHint("javax.persistence.fetchgraph", eg);

        Post post = (Post) query.getSingleResult();
        return Optional.ofNullable(post);
    }

    @Override
    public void deleteById(Integer id) {
        Post post = em.find(Post.class, id);
        em.remove(post);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Post> findTop(Pageable pageable) {
        Query query = em
                .createQuery("SELECT DISTINCT post FROM Post post JOIN post.pointPost ORDER BY post.points DESC");
        EntityGraph<Post> eg = em.createEntityGraph(Post.class);
        eg.addAttributeNodes("user");
        eg.addSubgraph("comment").addAttributeNodes("pointComment");
        eg.addSubgraph("pointPost").addAttributeNodes("user");
        query.setHint("javax.persistence.fetchgraph", eg);
        return new PageImpl<>(query
                .setMaxResults(10)
                .getResultList());
    }
}

package pl.romanek.blog.repository.springdatajpa;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.repository.PostRepository;

@Profile("spring-data-jpa")
public interface SpringDataPostRepository extends JpaRepository<Post, Integer>, PostRepository {

    // Queries not needed - only for educational purpose
    // @Query(value = "SELECT * FROM post WHERE user_id = :id", nativeQuery = true)
    // @Query(value = "SELECT p FROM Post p WHERE p.user.id = :id")
    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user", "comment" })
    Page<Post> findAllByUserIdOrderByCreatedDesc(Integer id, Pageable pageable);

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user", "comment" })
    Page<Post> findAllByOrderByCreatedDesc(Pageable pageable);

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user", "comment" })
    Optional<Post> findById(Integer id);

    @Override
    @Query(value = "SELECT post.id, post.text, post.user_id, post.created, post.last_modified, post.img FROM post, points_post WHERE post.id = points_post.post_id GROUP BY points_post.post_id ORDER BY COUNT(points_post.post_id) DESC LIMIT 10", nativeQuery = true)
    List<Post> findTop10();
}
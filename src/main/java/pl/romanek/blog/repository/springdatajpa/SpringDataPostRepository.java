package pl.romanek.blog.repository.springdatajpa;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import pl.romanek.blog.entity.Post;
import pl.romanek.blog.repository.PostRepository;

@Profile("spring-data-jpa")
public interface SpringDataPostRepository extends JpaRepository<Post, Integer>, PostRepository {

    // Queries not needed - only for educational purpose
    // @Query(value = "SELECT * FROM post WHERE user_id = :id", nativeQuery = true)
    // @Query(value = "SELECT p FROM Post p WHERE p.user.id = :id")
    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user" })
    List<Post> findAllByUserId(Integer id);

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user" })
    List<Post> findAll();

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user" })
    Optional<Post> findById(Integer id);
}
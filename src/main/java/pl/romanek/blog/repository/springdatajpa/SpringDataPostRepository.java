package pl.romanek.blog.repository.springdatajpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.romanek.blog.entity.Post;
import pl.romanek.blog.repository.PostRepository;

public interface SpringDataPostRepository extends JpaRepository<Post, Integer>, PostRepository {

    // Queries not needed - only for educational purpose
    // @Query(value = "SELECT * FROM post WHERE user_id = :id", nativeQuery = true)
    @Query(value = "SELECT p FROM Post p WHERE p.user.id = :id")
    List<Post> findAllByUserId(Integer id);
}

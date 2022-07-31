package pl.romanek.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.romanek.blog.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    // @Query(value = "SELECT * FROM post WHERE user_id = :id", nativeQuery = true)
    @Query(value = "SELECT p FROM Post p WHERE p.user.id = :id")
    List<Post> findAllPostsByUserId(Integer id);

}

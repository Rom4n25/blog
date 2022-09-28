package pl.romanek.blog.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import pl.romanek.blog.entities.Post;

@Repository
public interface PostRepository {

    Post save(Post post);

    List<Post> findAll();

    List<Post> findAllByUserId(Integer id);

    Optional<Post> findById(Integer id);
}

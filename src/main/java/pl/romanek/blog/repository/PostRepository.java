package pl.romanek.blog.repository;

import java.util.List;
import java.util.Optional;
import pl.romanek.blog.entity.Post;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAll();

    List<Post> findAllByUserId(Integer id);

    Optional<Post> findById(Integer id);
}

package pl.romanek.blog.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.romanek.blog.entity.Post;

public interface PostRepository {

    Post save(Post post);

    Page<Post> findAllByOrderByCreatedDesc(Pageable pageable);

    Page<Post> findAllByUserIdOrderByCreatedDesc(Integer id, Pageable pageable);

    Optional<Post> findById(Integer id);

    void deleteById(Integer id);

    Page<Post> findTop(Pageable pageable);
}

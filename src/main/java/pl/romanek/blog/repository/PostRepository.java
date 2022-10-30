package pl.romanek.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pl.romanek.blog.entity.Post;

public interface PostRepository {

    Post save(Post post);

    Page<Post> findAll(Pageable pageable);

    List<Post> findAllByUserId(Integer id);

    Optional<Post> findById(Integer id);
}

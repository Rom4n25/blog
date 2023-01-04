package pl.romanek.blog.repository;

import java.util.List;
import java.util.Optional;

import pl.romanek.blog.entity.Comment;

public interface CommentRepository {

    List<Comment> findAllByPostIdOrderByCreatedAsc(Integer id);

    List<Comment> findAll();

    Optional<Comment> findById(Integer id);

    Comment save(Comment comment);

    void deleteById(Integer id);
}

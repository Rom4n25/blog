package pl.romanek.blog.repository;

import java.util.List;
import pl.romanek.blog.entity.Comment;

public interface CommentRepository {

    List<Comment> findAllByPostIdOrderByCreatedAsc(Integer id);

    List<Comment> findAll();

    Comment save(Comment comment);
}

package pl.romanek.blog.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import pl.romanek.blog.entities.Comment;

@Repository
public interface CommentRepository {

    List<Comment> findAllByPostId(Integer id);

    List<Comment> findAll();

    Comment save(Comment comment);
}

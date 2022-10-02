package pl.romanek.blog.repository.springdatajpa;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.repository.CommentRepository;

@Profile("spring-data-jpa")
public interface SpringDataCommentRepository extends JpaRepository<Comment, Integer>, CommentRepository {

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user", "post" })
    List<Comment> findAllByPostId(Integer id);

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user", "post" })
    List<Comment> findAll();
}

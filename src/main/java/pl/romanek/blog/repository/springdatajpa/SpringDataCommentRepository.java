package pl.romanek.blog.repository.springdatajpa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.repository.CommentRepository;

@Profile("spring-data-jpa")
public interface SpringDataCommentRepository extends JpaRepository<Comment, Integer>, CommentRepository {

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user", "post", "pointComment"
    })
    List<Comment> findAllByPostIdOrderByCreatedAsc(Integer id);

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user", "post", "pointComment"
    })
    List<Comment> findAll();

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "user", "post", "pointComment"
    })
    Optional<Comment> findById(Integer id);
}

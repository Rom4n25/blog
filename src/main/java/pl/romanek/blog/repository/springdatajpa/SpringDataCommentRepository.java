package pl.romanek.blog.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.repository.CommentRepository;

@Profile("spring-data-jpa")
public interface SpringDataCommentRepository extends JpaRepository<Comment, Integer>, CommentRepository {
}

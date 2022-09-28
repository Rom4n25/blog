package pl.romanek.blog.repository.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.repository.CommentRepository;

public interface SpringDataCommentRepository extends JpaRepository<Comment, Integer>, CommentRepository {

}

package pl.romanek.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.romanek.blog.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // @Query("SELECT c.text FROM Comment c WHERE c.post.id = :postId")
    List<Comment> getAllCommentsByPostId(Integer postId);
}

package pl.romanek.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.CommentRepository;

@Service
@Transactional
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Transactional(readOnly = true)
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public Comment addComment(Comment comment, Integer userId, Integer postId) {
        User user = userService.findUserById(userId).get();
        Post post = postService.findPostById(postId).get();
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreated(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsInPost(Integer postId) {
        return commentRepository.findAllByPostIdOrderByCreatedAsc(postId);
    }
}

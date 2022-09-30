package pl.romanek.blog.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Transactional
    public void addComment(Comment comment, Integer userId, Integer postId) {
        User user = userService.findUserById(userId).get();
        Post post = postService.findPostById(postId);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsInPost(Integer postId) {
        return commentRepository.findAllByPostId(postId);
    }
}

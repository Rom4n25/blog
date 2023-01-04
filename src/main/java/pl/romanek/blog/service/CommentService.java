package pl.romanek.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.exception.UnauthorizedOperationException;
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

    public void editCommentById(Comment editedComment, Integer id, Integer userId) {
        Comment comment = findCommentById(id).orElseThrow();

        if (comment.getUser().getId() != userId)
            throw new UnauthorizedOperationException();

        comment.setLastModified(LocalDateTime.now());
        comment.setText(editedComment.getText());

        byte[] image = editedComment.getImg();
        Boolean imageHasContent = false;

        if (image != null) {
            imageHasContent = image.length > 0;
        }

        if (imageHasContent) {
            comment.setImg(image);
        } else {
            comment.setImg(null);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Comment> findCommentById(Integer id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllCommentsInPost(Integer postId) {
        return commentRepository.findAllByPostIdOrderByCreatedAsc(postId);
    }

    public void deleteById(Integer id, Integer userId) {

        if (commentRepository.findById(id).get().getUser().getId() != userId) {
            throw new UnauthorizedOperationException();
        }
        commentRepository.deleteById(id);
    }
}

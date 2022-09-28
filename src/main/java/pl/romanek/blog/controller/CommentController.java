package pl.romanek.blog.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.romanek.blog.entities.Comment;
import pl.romanek.blog.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public List<Comment> getAllComments() {
        return commentService.findAllComments();
    }

    @GetMapping("/post/{id}")
    public List<Comment> getAllCommentsInPost(@PathVariable("id") Integer id) {
        return commentService.findAllCommentsInPost(id);
    }

    @PostMapping("/add/{userId}/{postId}")
    public void addComment(@RequestBody Comment comment, @PathVariable("userId") Integer userId,
            @PathVariable("postId") Integer postId) {

        commentService.addComment(comment, userId, postId);
    }

}

package pl.romanek.blog.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.romanek.blog.entities.Comment;
import pl.romanek.blog.services.CommentService;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public List<Comment> getAllComments() {
        return commentService.findAllComments();
    }

    @GetMapping("comments/post")
    public List<Comment> getAllCommentsInPost(@RequestParam("id") Integer id) {
        return commentService.findAllCommentsInPost(id);
    }

    @PostMapping("/comment")
    public void addComment(@RequestBody Comment comment, @RequestParam("userId") Integer userId,
            @RequestParam("postId") Integer postId) {

        commentService.addComment(comment, userId, postId);
    }

}

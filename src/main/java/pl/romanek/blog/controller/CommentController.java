package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = new ArrayList<>(commentService.findAllComments());
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<Comment>> getAllCommentsInPost(@PathVariable("id") Integer id) {
        List<Comment> comments = new ArrayList<>(commentService.findAllCommentsInPost(id));
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/add/{userId}/{postId}")
    public ResponseEntity<Void> addComment(@RequestBody Comment comment, @PathVariable("userId") Integer userId,
            @PathVariable("postId") Integer postId) {

        commentService.addComment(comment, userId, postId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

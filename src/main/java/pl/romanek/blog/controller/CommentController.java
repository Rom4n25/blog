package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.romanek.blog.dto.CommentDto;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.mapper.CommentMapper;
import pl.romanek.blog.security.SecurityUser;
import pl.romanek.blog.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<Comment> comments = new ArrayList<>(commentService.findAllComments());
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(commentMapper.toCommentsDto(comments));
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsInPost(@PathVariable("id") Integer id) {
        List<Comment> comments = new ArrayList<>(commentService.findAllCommentsInPost(id));
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(commentMapper.toCommentsDto(comments));
    }

    @PostMapping("/add/{postId}")
    public ResponseEntity<Void> addComment(@RequestBody CommentDto commentDto,
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable("postId") Integer postId) {

        commentService.addComment(commentMapper.toCommentEntity(commentDto), securityUser.getId(), postId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

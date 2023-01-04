package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import pl.romanek.blog.dto.CommentRequestDto;
import pl.romanek.blog.dto.CommentResponseDto;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.mapper.CommentRequestMapper;
import pl.romanek.blog.mapper.CommentResponseMapper;
import pl.romanek.blog.service.CommentService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRequestMapper commentRequestMapper;
    private final CommentResponseMapper commentResponseMapper;

    @GetMapping("/all")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<Comment> comments = new ArrayList<>(commentService.findAllComments());
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(commentResponseMapper.toCommentsResponseDto(comments));
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsInPost(@PathVariable("id") Integer id) {
        List<Comment> comments = new ArrayList<>(commentService.findAllCommentsInPost(id));
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(commentResponseMapper.toCommentsResponseDto(comments));
    }

    @PostMapping("/add")
    public ResponseEntity<CommentResponseDto> addComment(CommentRequestDto commentDto,
            Authentication authentication) {
        Integer userId = Integer.parseInt(authentication.getPrincipal().toString());
        Comment comment = commentService.addComment(commentRequestMapper.toCommentEntity(commentDto), userId,
                commentDto.getPostId());
        return ResponseEntity.ok(commentResponseMapper.toCommentResponseDto(comment));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Void> editCommentById(@PathVariable("id") Integer id, CommentRequestDto postRequestDto,
            Authentication authentication) {

        Integer userId = Integer.parseInt(authentication.getPrincipal().toString());

        commentService.editCommentById(commentRequestMapper.toCommentEntity(postRequestDto), id, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable("id") Integer id, Authentication authentication) {

        Integer userId = Integer.parseInt(authentication.getPrincipal().toString());

        commentService.deleteById(id, userId);

        return ResponseEntity.ok().build();
    }
}

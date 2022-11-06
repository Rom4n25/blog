package pl.romanek.blog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import pl.romanek.blog.dto.CommentRequestDto;
import pl.romanek.blog.dto.CommentResponseDto;
import pl.romanek.blog.entity.Comment;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.mapper.CommentRequestMapper;
import pl.romanek.blog.mapper.CommentResponseMapper;
import pl.romanek.blog.service.CommentService;
import pl.romanek.blog.service.PostService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRequestMapper commentRequestMapper;
    private final CommentResponseMapper commentResponseMapper;
    private final PostService postService;

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

    @PostMapping("/add/{postId}")
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto commentDto,
            Authentication authentication,
            @PathVariable("postId") Integer postId) {
        Integer userId = Integer.parseInt(authentication.getPrincipal().toString());
        Comment comment = commentService.addComment(commentRequestMapper.toCommentEntity(commentDto), userId, postId);
        return ResponseEntity.ok(commentResponseMapper.toCommentResponseDto(comment));
    }

    @PostMapping(path = "/img/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CommentResponseDto> addCommentImg(@RequestParam("file") MultipartFile file,
            @RequestParam("postId") Integer postId, @RequestParam("commentId") Integer commentId,
            Authentication authentication) throws IOException {

        Integer userId = Integer.parseInt(authentication.getPrincipal().toString());
        Optional<Post> post = postService.findPostById(postId);

        if (post.isPresent()) {
            post.get().getComment().stream().filter(comment -> comment.getId().equals(commentId)).findFirst().get()
                    .setImg(file.getBytes());
            postService.addPost(post.get(), userId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

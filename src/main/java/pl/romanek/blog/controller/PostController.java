package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import pl.romanek.blog.dto.PostRequestDto;
import pl.romanek.blog.dto.PostResponseDto;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.mapper.PostResponseMapper;
import pl.romanek.blog.mapper.PostRequestMapper;
import pl.romanek.blog.security.SecurityUser;
import pl.romanek.blog.service.PostService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRequestMapper postRequestMapper;
    private final PostResponseMapper postResponseMapper;

    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<Post> posts = new ArrayList<>(postService.findAllPosts());
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(postResponseMapper.toPostsResponseDto(posts));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostResponseDto>> getAllPostByUserId(@PathVariable("id") Integer id) {
        List<Post> posts = new ArrayList<>(postService.findAllPostsByUserId(id));
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(postResponseMapper.toPostsResponseDto(posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable("id") Integer id) {
        Optional<Post> post = postService.findPostById(id);
        return ResponseEntity.of(Optional.ofNullable(postResponseMapper.toPostResponseDto(post.get())));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addPost(@RequestBody PostRequestDto postRequestDto,
            @AuthenticationPrincipal SecurityUser securityUser) {
        postService.addPost(postRequestMapper.toPostEntity(postRequestDto), securityUser.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

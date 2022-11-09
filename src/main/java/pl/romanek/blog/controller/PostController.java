package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import pl.romanek.blog.dto.PostRequestDto;
import pl.romanek.blog.dto.PostResponseDto;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.mapper.PostResponseMapper;
import pl.romanek.blog.mapper.PostRequestMapper;
import pl.romanek.blog.service.PostService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRequestMapper postRequestMapper;
    private final PostResponseMapper postResponseMapper;

    @GetMapping("/all/{page}")
    public ResponseEntity<List<PostResponseDto>> getAllPosts(@PathVariable("page") Integer page) {
        List<Post> posts = new ArrayList<>(postService.findAllPosts(page).toList());

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(postResponseMapper.toPostsResponseDto(posts));
    }

    @GetMapping("/user/{id}/{page}")
    public ResponseEntity<List<PostResponseDto>> getAllPostByUserId(@PathVariable("id") Integer id,
            @PathVariable("page") Integer page) {
        List<Post> posts = new ArrayList<>(postService.findAllPostsByUserId(id, page).toList());
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
    public ResponseEntity<PostResponseDto> addPost(PostRequestDto postRequestDto,
            Authentication authentication) {

        Integer userId = Integer.parseInt(authentication.getPrincipal().toString());
        Post post = postService.addPost(postRequestMapper.toPostEntity(postRequestDto),
                userId);
        return ResponseEntity.ok(postResponseMapper.toPostResponseDto(post));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Void> editPostById(@PathVariable("id") Integer id, PostRequestDto postRequestDto,
            Authentication authentication) {

        Integer userId = Integer.parseInt(authentication.getPrincipal().toString());

        postService.editPostById(postRequestMapper.toPostEntity(postRequestDto), id, userId);

        return ResponseEntity.ok().build();
    }
}

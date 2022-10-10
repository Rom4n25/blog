package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.romanek.blog.dto.PostDto;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.mapper.PostMapper;
import pl.romanek.blog.security.SecurityUser;
import pl.romanek.blog.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @Autowired
    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;

    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> posts = new ArrayList<>(postService.findAllPosts());
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(postMapper.toPostsDto(posts));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostDto>> getAllPostByUserId(@PathVariable("id") Integer id) {
        List<Post> posts = new ArrayList<>(postService.findAllPostsByUserId(id));
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(postMapper.toPostsDto(posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Integer id) {
        Optional<Post> post = postService.findPostById(id);
        return ResponseEntity.of(Optional.ofNullable(postMapper.toPostDto(post.get())));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addPost(@RequestBody PostDto postDto,
            @AuthenticationPrincipal SecurityUser securityUser) {
        postService.addPost(postMapper.toPostEntity(postDto), securityUser.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

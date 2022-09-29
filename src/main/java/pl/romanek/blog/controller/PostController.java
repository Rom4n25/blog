package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.romanek.blog.entity.Post;
import pl.romanek.blog.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = new ArrayList<>(postService.findAllPosts());
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Post>> getAllPostByUserId(@PathVariable("id") Integer id) {
        List<Post> posts = new ArrayList<>(postService.findAllPostsByUserId(id));
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<Void> addPost(@RequestBody Post post, @PathVariable("userId") Integer userId) {
        postService.addPost(post, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

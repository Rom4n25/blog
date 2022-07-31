package pl.romanek.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.romanek.blog.entities.Post;
import pl.romanek.blog.services.PostService;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }

    @GetMapping("/posts/user")
    public List<Post> getAllPostByUserId(@RequestParam("id") Integer id) {
        return postService.findAllPostsByUserId(id);
    }

    @PostMapping("/post")
    public void addPost(@RequestBody Post post, @RequestParam("userId") Integer userId) {
        postService.addPost(post, userId);
    }
}

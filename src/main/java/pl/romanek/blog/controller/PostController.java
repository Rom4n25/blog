package pl.romanek.blog.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }

    @GetMapping("/user/{id}")
    public List<Post> getAllPostByUserId(@PathVariable("id") Integer id) {
        return postService.findAllPostsByUserId(id);
    }

    @PostMapping("/add/{userId}")
    public void addPost(@RequestBody Post post, @PathVariable("userId") Integer userId) {
        postService.addPost(post, userId);
    }
}

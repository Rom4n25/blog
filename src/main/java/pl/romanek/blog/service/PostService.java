package pl.romanek.blog.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.romanek.blog.entity.Post;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public void addPost(Post post, Integer id) {
        User user = userService.findUserById(id).get();
        post.setUser(user);
        postRepository.save(post);
    }

    public List<Post> findAllPostsByUserId(Integer id) {
        return postRepository.findAllByUserId(id);
    }

    public Post findPostById(Integer id) {
        return postRepository.findById(id).get();
    }

}

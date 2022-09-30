package pl.romanek.blog.service;

import java.util.List;
import java.util.Optional;
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

    @Transactional(readOnly = true)
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public void addPost(Post post, Integer id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            post.setUser(user.get());
            postRepository.save(post);
        }
    }

    @Transactional(readOnly = true)
    public List<Post> findAllPostsByUserId(Integer id) {
        return postRepository.findAllByUserId(id);
    }

    @Transactional(readOnly = true)
    public Post findPostById(Integer id) {
        return postRepository.findById(id).get();
    }
}

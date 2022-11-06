package pl.romanek.blog.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.PostRepository;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public Page<Post> findAllPosts(Integer page) {
        return postRepository.findAllByOrderByCreatedDesc(PageRequest.of(page, 10));
    }

    public Post addPost(Post post, Integer id) {
        Post savedPost = null;
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            post.setUser(user.get());
            post.setCreated(LocalDateTime.now());
            savedPost = postRepository.save(post);
        }
        return savedPost;
    }

    @Transactional(readOnly = true)
    public Page<Post> findAllPostsByUserId(Integer id, Integer page) {
        return postRepository.findAllByUserIdOrderByCreatedDesc(id, PageRequest.of(page, 10));
    }

    @Transactional(readOnly = true)
    public Optional<Post> findPostById(Integer id) {
        return postRepository.findById(id);
    }
}

package pl.romanek.blog.service;

import java.util.List;
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
        return postRepository.findAll(PageRequest.of(page, 10));
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
    public Optional<Post> findPostById(Integer id) {
        return postRepository.findById(id);
    }
}

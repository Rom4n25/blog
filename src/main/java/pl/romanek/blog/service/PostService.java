package pl.romanek.blog.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import pl.romanek.blog.entity.PointPost;
import pl.romanek.blog.entity.Post;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.exception.PointAlreadyAddedException;
import pl.romanek.blog.exception.UnauthorizedOperationException;
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

    public void editPostById(Post editedPost, Integer id, Integer userId) {
        Post post = findPostById(id).orElseThrow();

        if (post.getUser().getId() != userId)
            throw new UnauthorizedOperationException();

        post.setLastModified(LocalDateTime.now());
        post.setText(editedPost.getText());

        byte[] image = editedPost.getImg();
        Boolean imageHasContent = false;

        if (image != null) {
            imageHasContent = image.length > 0;
        }

        if (imageHasContent) {
            post.setImg(image);
        } else {
            post.setImg(null);
        }
    };

    @Transactional(readOnly = true)
    public Page<Post> findAllPostsByUserId(Integer id, Integer page) {
        return postRepository.findAllByUserIdOrderByCreatedDesc(id, PageRequest.of(page, 10));
    }

    @Transactional(readOnly = true)
    public Optional<Post> findPostById(Integer id) {
        return postRepository.findById(id);
    }

    public void deletePostById(Integer id, Integer userId) {

        Post post = postRepository.findById(id).orElseThrow();

        if (post.getUser().getId() != userId) {
            throw new UnauthorizedOperationException();
        }

        postRepository.deleteById(id);
    }

    public void addPoint(Integer id, Integer userId) {

        Post post = postRepository.findById(id).orElseThrow();
        User user = userService.findUserById(userId).orElseThrow();
        PointPost point = new PointPost();
        point.setPost(post);
        point.setUser(user);

        if (!post.getPointPost().stream().map(p -> p.getUser().getId()).anyMatch(s -> s == userId)) {
            post.setPoints(post.getPointPost().size() + 1);
            post.getPointPost().add(point);
        } else {
            throw new PointAlreadyAddedException();
        }
    }

    public Page<Post> findTopPosts() {
        return postRepository.findTop(Pageable.ofSize(10));
    }
}

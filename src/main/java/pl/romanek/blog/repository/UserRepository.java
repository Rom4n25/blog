package pl.romanek.blog.repository;

import java.util.List;
import java.util.Optional;
import pl.romanek.blog.entity.User;

public interface UserRepository {

    Optional<User> findById(Integer id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void deleteById(Integer id);

    void deleteByUsername(String username);
}

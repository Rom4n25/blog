package pl.romanek.blog.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import pl.romanek.blog.entities.User;

@Repository
public interface UserRepository {

    Optional<User> findById(Integer id);

    List<User> findAll();

    User save(User user);

    void deleteById(Integer id);
}

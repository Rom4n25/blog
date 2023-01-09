package pl.romanek.blog.repository.springdatajpa;

import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.UserRepository;

@Profile("spring-data-jpa")
public interface SpringDataUserRepository extends JpaRepository<User, Integer>, UserRepository {

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "role" })
    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

    @Override
    @EntityGraph(type = EntityGraphType.FETCH, attributePaths = { "role" })
    List<User> findAll();
}

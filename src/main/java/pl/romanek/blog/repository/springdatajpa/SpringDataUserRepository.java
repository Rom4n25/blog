package pl.romanek.blog.repository.springdatajpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.UserRepository;

public interface SpringDataUserRepository extends JpaRepository<User, Integer>, UserRepository {

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);
}

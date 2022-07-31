package pl.romanek.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.romanek.blog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}

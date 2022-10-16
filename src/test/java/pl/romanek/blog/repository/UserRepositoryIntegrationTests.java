package pl.romanek.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.romanek.blog.entity.Role;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.springdatajpa.SpringDataUserRepository;
import pl.romanek.blog.security.RoleName;

@DataJpaTest
public class UserRepositoryIntegrationTests {

    @Autowired
    SpringDataUserRepository springDataUserRepository;

    @BeforeEach
    public void addUser() {

        User user = new User();
        user.setId(1);
        user.setUsername("Bill");
        user.setPassword("password");
        user.setRole(new Role(RoleName.USER));

        springDataUserRepository.save(user);
    }

    @Test
    public void shouldFindUserByUsername() {
        assertEquals(1, springDataUserRepository.findByUsername("Bill").get().getId());
    }

    @Test
    public void shouldGetEmptyOptionalWhenUserNotFoundByUsername() {
        assertEquals(Optional.empty(), springDataUserRepository.findByUsername("Steve"));
    }

    @Test
    public void shouldDeleteUserByUsername() {
        springDataUserRepository.deleteByUsername("Bill");
        assertEquals(Optional.empty(), springDataUserRepository.findByUsername("Bill"));
    }
}

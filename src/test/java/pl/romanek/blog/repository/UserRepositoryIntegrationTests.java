package pl.romanek.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.romanek.blog.entity.Role;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.springdatajpa.SpringDataUserRepository;
import pl.romanek.blog.security.RoleName;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryIntegrationTests {

    @Autowired
    SpringDataUserRepository springDataUserRepository;

    @BeforeEach
    public void addUser() {

        User user = User.builder()
                .id(1)
                .username("Bill")
                .password("password")
                .role(new Role(RoleName.USER))
                .build();

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

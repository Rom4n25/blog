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
import pl.romanek.blog.security.RoleName;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryIntegrationTests {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void addUser() {

        User user = User.builder()
                .id(1)
                .username("Bill")
                .password("password")
                .role(new Role(RoleName.USER))
                .build();

        userRepository.save(user);
    }

    @Test
    public void shouldFindUserByUsername() {
        assertEquals(1,
                userRepository.findByUsername("Bill").get().getId());
    }

    @Test
    public void shouldGetEmptyOptionalWhenUserNotFoundByUsername() {
        assertEquals(Optional.empty(),
                userRepository.findByUsername("Steve"));
    }

    @Test
    public void shouldDeleteUserByUsername() {
        userRepository.deleteByUsername("Bill");
        assertEquals(Optional.empty(),
                userRepository.findByUsername("Bill"));
    }
}

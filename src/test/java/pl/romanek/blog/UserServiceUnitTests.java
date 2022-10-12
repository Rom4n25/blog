package pl.romanek.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.RoleRepository;
import pl.romanek.blog.repository.UserRepository;
import pl.romanek.blog.service.UserService;

public class UserServiceUnitTests {

    UserRepository userRepository = mock(UserRepository.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    RoleRepository roleRepository = mock(RoleRepository.class);

    @Test
    void shouldGetAllUsers() {
        UserService userService = new UserService(userRepository, roleRepository, passwordEncoder);
        assertEquals(userService.findAllUsers(), new ArrayList<User>());
    }

    @Test
    void shouldGetUserById() {
        UserService userService = new UserService(userRepository, roleRepository, passwordEncoder);
        User user = new User();
        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        assertEquals(userService.findUserById(2), Optional.of(user));
    }

    @Test
    void shouldGetEmptyOptionalWhenUserNotFoundById() {
        UserService userService = new UserService(userRepository, roleRepository, passwordEncoder);
        when(userRepository.findById(999)).thenReturn(Optional.empty());
        assertEquals(userService.findUserById(2), Optional.empty());
    }
}

package pl.romanek.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.romanek.blog.entity.Role;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.exception.UnauthorizedOperationException;
import pl.romanek.blog.exception.UsernameExistsException;
import pl.romanek.blog.repository.RoleRepository;
import pl.romanek.blog.repository.UserRepository;
import pl.romanek.blog.security.RoleName;
import pl.romanek.blog.security.SecurityUser;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleRepository roleRepository;

    @Mock
    SecurityUser securityUser;

    @InjectMocks
    UserService userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Captor
    ArgumentCaptor<Role> roleCaptor;

    List<User> users;

    @BeforeEach
    public void initUsers() {
        User user = User.builder().build();
        users = new ArrayList<User>();
        users.add(user);
    }

    @Test
    public void shouldGetAllUsers() {
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(userService.findAllUsers(), users);
    }

    @Test
    public void shouldAddUser() {
        userService.addUser(users.get(0));
        verify(userRepository).save(userCaptor.capture());
        verify(roleRepository).save(roleCaptor.capture());

        assertEquals(users.get(0), userCaptor.getValue());
        assertEquals(RoleName.USER, roleCaptor.getValue().getRoleName());
    }

    @Test
    public void shouldNotAddUserIfUsernameExists() {
        when(userRepository.findByUsername(users.get(0).getUsername())).thenReturn(Optional.of(users.get(0)));
        assertThrows(UsernameExistsException.class, ()-> userService.addUser(users.get(0)));
    }

    @Test
    public void shouldGetUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(users.get(0)));
        assertEquals(Optional.of(users.get(0)),userService.findUserById(1));
    }

    @Test
    public void shouldGetEmptyOptionalWhenUserNotFoundById() {
        when(userRepository.findById(999)).thenReturn(Optional.empty());
        assertEquals(userService.findUserById(999), Optional.empty());
    }

    @Test
    public void shouldGetUserByUsername() {
        when(userRepository.findByUsername("Bill")).thenReturn(Optional.of(users.get(0)));
        assertEquals(Optional.of(users.get(0)), userService.findUserByUsername("Bill"));
    }

    @Test
    public void shouldGetEmptyOptionalWhenUserNotFoundByUsername() {
        when(userRepository.findByUsername("Bill")).thenReturn(Optional.empty());
        assertEquals(userService.findUserByUsername("Bill"), Optional.empty());
    }

    @Test
    public void shouldUserDeleteHimselfById() {
        when(securityUser.getId()).thenReturn(1);
        userService.deleteUserById(1, securityUser);
        verify(userRepository).deleteById(1);
    }

    @Test
    public void shouldNotDeleteUser() {
        when(securityUser.getId()).thenReturn(2);
        assertThrows(UnauthorizedOperationException.class, () -> userService.deleteUserById(1, securityUser));
    }
}

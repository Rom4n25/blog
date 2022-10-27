package pl.romanek.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import pl.romanek.blog.entity.Role;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.exception.UnauthorizedOperationException;
import pl.romanek.blog.exception.UsernameExistsException;
import pl.romanek.blog.repository.RoleRepository;
import pl.romanek.blog.repository.UserRepository;
import pl.romanek.blog.security.RoleName;
import pl.romanek.blog.security.SecurityUser;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameExistsException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(new Role(RoleName.USER));
        roleRepository.save(user.getRole());
        userRepository.save(user);
    }

    public void deleteUserById(Integer id, SecurityUser securityUser) {
        if (id.equals(securityUser.getId())
                || securityUser.getAuthorities().stream().anyMatch(s -> s.getAuthority().equals("ROLE_ADMIN"))) {
            userRepository.deleteById(id);
        } else {
            throw new UnauthorizedOperationException();
        }
    }

    public void deleteUserByUsername(String username, Integer userId, SecurityUser securityUser) {
        if (userId.equals(securityUser.getId())
                || securityUser.getAuthorities().stream().anyMatch(s -> s.getAuthority().equals("ROLE_ADMIN"))) {
            userRepository.deleteByUsername(username);
        } else {
            throw new UnauthorizedOperationException();
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> u = userRepository.findByUsername(username);
        return u.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public ResponseCookie logout() {
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .maxAge(0)
                .httpOnly(true)
                .path("/")
                .secure(true).build();
        return cookie;
    }
}

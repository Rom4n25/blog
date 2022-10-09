package pl.romanek.blog.service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.RoleRepository;
import pl.romanek.blog.repository.UserRepository;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleRepository.save(user.getRole());
        userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
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
}

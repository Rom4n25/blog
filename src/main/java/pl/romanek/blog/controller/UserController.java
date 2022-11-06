package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import pl.romanek.blog.dto.UserRequestDto;
import pl.romanek.blog.dto.UserResponseDto;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.mapper.UserRequestMapper;
import pl.romanek.blog.mapper.UserResponseMapper;
import pl.romanek.blog.security.SecurityUser;
import pl.romanek.blog.service.UserService;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = new ArrayList<>(userService.findAllUsers());
        return users.isEmpty() ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(userResponseMapper.toUsersResponseDto(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") Integer id) {
        Optional<User> user = userService.findUserById(id);
        return ResponseEntity.of(Optional.ofNullable(userResponseMapper.toUserResponseDto(user.orElse(null))));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable("username") String username) {
        Optional<User> user = userService.findUserByUsername(username);
        return ResponseEntity.of(Optional.ofNullable(userResponseMapper.toUserResponseDto(user.orElse(null))));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addUser(@Valid @RequestBody UserRequestDto userDto) {
        userService.addUser(userRequestMapper.toUserEntity(userDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Integer id,
            @AuthenticationPrincipal SecurityUser securityUser) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            userService.deleteUserById(id, securityUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/name/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable("username") String username,
            @AuthenticationPrincipal SecurityUser securityUser) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            Integer userId = user.get().getId();
            userService.deleteUserByUsername(username, userId, securityUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logOut(HttpServletResponse response) {
        ResponseCookie cookie = userService.logout();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> logIn() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

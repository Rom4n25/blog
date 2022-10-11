package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.romanek.blog.dto.UserRequestDto;
import pl.romanek.blog.dto.UserResponseDto;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.mapper.UserRequestMapper;
import pl.romanek.blog.mapper.UserResponseMapper;
import pl.romanek.blog.security.SecurityUser;
import pl.romanek.blog.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    @Autowired
    public UserController(UserService userService, UserRequestMapper userRequestMapper,
            UserResponseMapper userResponseMapper) {
        this.userService = userService;
        this.userRequestMapper = userRequestMapper;
        this.userResponseMapper = userResponseMapper;
    }

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

    @PostMapping("/add")
    public ResponseEntity<Void> addUser(@RequestBody UserRequestDto userDto) {
        userService.addUser(userRequestMapper.toUserEntity(userDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Integer id,
            @ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            userService.deleteUserById(id, securityUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/name/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable("username") String username,
            @ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            Integer userId = user.get().getId();
            userService.deleteUserByUsername(username, userId, securityUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

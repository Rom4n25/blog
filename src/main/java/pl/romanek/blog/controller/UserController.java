package pl.romanek.blog.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.romanek.blog.dto.UserDto;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = new ArrayList<>(userService.findAllUsers());
        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(convertToDto(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Integer id) {
        Optional<User> user = userService.findUserById(id);
        return ResponseEntity.of(Optional.ofNullable(convertToDto(user.get())));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addUser(UserDto userDto) {
        userService.addUser(convertToEntity(userDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Integer id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/name/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable("username") String username) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            userService.deleteUserByUsername(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public UserDto convertToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }

    public User convertToEntity(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    public List<UserDto> convertToDto(List<User> user) {
        return Arrays.asList(this.modelMapper.map(user, UserDto[].class));

    }

    public List<User> convertToEntity(List<UserDto> userDto) {
        return Arrays.asList(this.modelMapper.map(userDto, User[].class));
    }
}

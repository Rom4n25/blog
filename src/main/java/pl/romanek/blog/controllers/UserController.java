package pl.romanek.blog.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.romanek.blog.entities.User;
import pl.romanek.blog.services.UserService;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/user")
    public User getUser(@RequestParam("id") Integer id) {
        return userService.findUserById(id);
    }

    @PostMapping("/user")
    public void addUser(User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUser(id);
    }
}

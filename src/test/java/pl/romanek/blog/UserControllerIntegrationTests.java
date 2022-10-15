package pl.romanek.blog;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.romanek.blog.controller.UserController;
import pl.romanek.blog.entity.Role;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.mapper.UserRequestMapper;
import pl.romanek.blog.mapper.UserResponseMapper;
import pl.romanek.blog.security.RoleName;
import pl.romanek.blog.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTests {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @MockBean
        UserService userService;

        @MockBean
        UserRequestMapper userRequestMapper;

        @MockBean
        UserResponseMapper userResponseMapper;

        @WithMockUser(roles = "USER")
        @Test
        public void shouldGetAllUsers() throws Exception {
                User user1 = new User();
                user1.setId(1);
                user1.setUsername("Bill");
                user1.setPassword("password");
                user1.setRole(new Role(RoleName.USER));

                User user2 = new User();
                user2.setId(2);
                user2.setUsername("Steve");
                user2.setPassword("secret");
                user2.setRole(new Role(RoleName.USER));

                List<User> users = new ArrayList<User>(List.of(user1, user2));
                when(userService.findAllUsers()).thenReturn(users);
                when(userResponseMapper.toUsersResponseDto(users))
                                .thenReturn(UserResponseMapper.INSTANCE.toUsersResponseDto(users));
                String userAsJson = objectMapper
                                .writeValueAsString(UserResponseMapper.INSTANCE.toUsersResponseDto(users));
                mockMvc.perform(get("/users/all"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(userAsJson));
        }

        @WithMockUser(roles = "USER")
        @Test
        public void shouldGetUser() throws Exception {
                User user = new User();
                user.setId(1);
                user.setUsername("Bill");
                user.setPassword("password");
                user.setRole(new Role(RoleName.USER));

                when(userService.findUserById(1)).thenReturn(Optional.ofNullable(user));
                when(userResponseMapper.toUserResponseDto(user))
                                .thenReturn(UserResponseMapper.INSTANCE.toUserResponseDto(user));
                String userAsJson = objectMapper
                                .writeValueAsString(UserResponseMapper.INSTANCE.toUserResponseDto(user));
                mockMvc.perform(get("/users/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(userAsJson));
        }

        @WithMockUser(roles = "USER")
        @Test
        public void shouldNotGetUser() throws Exception {
                User user = null;
                when(userService.findUserById(1)).thenReturn(Optional.ofNullable(user));
                when(userResponseMapper.toUserResponseDto(user))
                                .thenReturn(UserResponseMapper.INSTANCE.toUserResponseDto(user));
                mockMvc.perform(get("/users/1"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string(""));
        }
}

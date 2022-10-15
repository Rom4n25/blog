package pl.romanek.blog;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.romanek.blog.advice.ExceptionControllerAdvice;
import pl.romanek.blog.config.SecurityConfig;
import pl.romanek.blog.controller.UserController;
import pl.romanek.blog.dto.UserRequestDto;
import pl.romanek.blog.dto.UserResponseDto;
import pl.romanek.blog.entity.Role;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.mapper.UserRequestMapper;
import pl.romanek.blog.mapper.UserResponseMapper;
import pl.romanek.blog.security.RoleName;
import pl.romanek.blog.service.UserService;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
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

        List<User> users;

        @BeforeEach
        public void initUsers() {
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

                users = new ArrayList<User>(List.of(user1, user2));
        }

        @WithMockUser(roles = "USER")
        @Test
        public void shouldGetAllUsers() throws Exception {
                List<UserResponseDto> userResponseDto = UserResponseMapper.INSTANCE.toUsersResponseDto(users);

                when(userService.findAllUsers()).thenReturn(users);
                when(userResponseMapper.toUsersResponseDto(users)).thenReturn(userResponseDto);

                String userAsJson = objectMapper.writeValueAsString(userResponseDto);
                mockMvc.perform(get("/users/all"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(userAsJson));
        }

        @WithMockUser(roles = "USER")
        @Test
        public void shouldNotFoundUsers() throws Exception {
                users.clear();
                List<UserResponseDto> userResponseDto = UserResponseMapper.INSTANCE.toUsersResponseDto(users);

                when(userService.findAllUsers()).thenReturn(users);
                when(userResponseMapper.toUsersResponseDto(users)).thenReturn(userResponseDto);

                mockMvc.perform(get("/users/all"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string(""));
        }

        @WithMockUser(roles = "USER")
        @Test
        public void shouldGetUser() throws Exception {
                UserResponseDto userResponseDto = UserResponseMapper.INSTANCE.toUserResponseDto(users.get(0));

                when(userService.findUserById(1)).thenReturn(Optional.ofNullable(users.get(0)));
                when(userResponseMapper.toUserResponseDto(users.get(0))).thenReturn(userResponseDto);
                String userAsJson = objectMapper.writeValueAsString(userResponseDto);
                mockMvc.perform(get("/users/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(userAsJson));
        }

        @WithMockUser(roles = "USER")
        @Test
        public void shouldNotFoundUser() throws Exception {
                when(userService.findUserById(1)).thenReturn(Optional.ofNullable(null));
                when(userResponseMapper.toUserResponseDto(null))
                                .thenReturn(UserResponseMapper.INSTANCE.toUserResponseDto(null));
                mockMvc.perform(get("/users/1"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string(""));
        }

        @Test
        public void shouldAddUser() throws Exception {
                UserRequestDto userRequestDto = new UserRequestDto();
                userRequestDto.setUsername("Bill");
                userRequestDto.setPassword("password");

                String userRequestDtoAsJson = objectMapper.writeValueAsString(userRequestDto);

                mockMvc.perform(post("/users/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userRequestDtoAsJson))
                                .andDo(print())
                                .andExpect(status().isCreated());
        }

        @Test
        public void shouldNotAddUserIfUsernameExists() throws Exception {

                MockMvcBuilders.standaloneSetup(userService).setControllerAdvice(new ExceptionControllerAdvice())
                                .build();

                UserRequestDto userRequestDto = new UserRequestDto();
                userRequestDto.setUsername("");
                userRequestDto.setPassword("");

                String userRequestDtoAsJson = objectMapper.writeValueAsString(userRequestDto);

                mockMvc.perform(post("/users/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userRequestDtoAsJson))
                                .andDo(print())
                                .andExpect(status().isConflict());

        }
}

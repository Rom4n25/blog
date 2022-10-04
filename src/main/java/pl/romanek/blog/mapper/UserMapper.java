package pl.romanek.blog.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import pl.romanek.blog.dto.UserDto;
import pl.romanek.blog.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUserEntity(UserDto userDto);

    List<UserDto> toUsersDto(List<User> users);

    List<User> toUsersEntity(List<UserDto> users);
}

package pl.romanek.blog.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.romanek.blog.dto.UserRequestDto;
import pl.romanek.blog.entity.User;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    @Mapping(target = "id", ignore = true)
    User toUserEntity(UserRequestDto userDto);

    List<User> toUsersEntity(List<UserRequestDto> users);
}

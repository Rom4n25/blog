package pl.romanek.blog.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.romanek.blog.dto.UserResponseDto;
import pl.romanek.blog.entity.User;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    UserResponseDto toUserResponseDto(User user);

    List<UserResponseDto> toUsersResponseDto(List<User> users);
}

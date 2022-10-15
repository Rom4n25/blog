package pl.romanek.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import pl.romanek.blog.dto.UserRequestDto;
import pl.romanek.blog.entity.User;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUserEntity(UserRequestDto userRequestDto);
}

package pl.romanek.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.romanek.blog.dto.PostRequestDto;
import pl.romanek.blog.entity.Post;

@Mapper(componentModel = "spring")
public interface PostRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    Post toPostEntity(PostRequestDto postRequestDto);
}

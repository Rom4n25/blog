package pl.romanek.blog.mapper;

import java.io.IOException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.romanek.blog.dto.PostRequestDto;
import pl.romanek.blog.entity.Post;

@Mapper(componentModel = "spring")
public interface PostRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "pointPost", ignore = true)
    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    @Mapping(source = "postRequestDto", target = "img", qualifiedByName = "img")
    Post toPostEntity(PostRequestDto postRequestDto);

    @Named("img")
    default byte[] img(PostRequestDto postRequestDto) throws IOException {
        return postRequestDto.getFile() == null ? null : postRequestDto.getFile().getBytes();

    }
}
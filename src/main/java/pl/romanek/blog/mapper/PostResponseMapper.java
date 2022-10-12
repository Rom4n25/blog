package pl.romanek.blog.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import pl.romanek.blog.dto.PostResponseDto;
import pl.romanek.blog.entity.Post;

@Mapper(componentModel = "spring")
public interface PostResponseMapper {

    PostResponseDto toPostResponseDto(Post Post);

    List<PostResponseDto> toPostsResponseDto(List<Post> Posts);
}
